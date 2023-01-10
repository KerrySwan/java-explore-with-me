package ru.practicum.explore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.EndpointHitDto;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.EventShortDto;
import ru.practicum.explore.commons.dto.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HitServiceImpl implements HitService {

    @Value("${stats-server.url}")
    private String serverUrl;
    private final ObjectMapper objectMapper;

    public HitServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void addHit(HttpServletRequest req) throws URISyntaxException, JsonProcessingException {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri(req.getRequestURL().toString())
                .timestamp(LocalDateTime.now())
                .ip(req.getRemoteAddr())
                .build();
        sendHit(endpointHitDto);
    }

    ;

    private void addHit(HttpServletRequest request, List<String> url) throws JsonProcessingException, URISyntaxException {
        List<EndpointHitDto> statsHitDtoList = url.stream()
                .map(uri -> EndpointHitDto.builder()
                        .app("ewm-main-service")
                        .uri(uri)
                        .timestamp(LocalDateTime.now())
                        .ip(request.getRemoteAddr())
                        .build())
                .collect(Collectors.toList());
        for (EndpointHitDto statsHitDto : statsHitDtoList) {
            sendHit(statsHitDto);
        }
    }


    @Override
    public EventFullDto getView(HttpServletRequest req, EventFullDto dto) throws URISyntaxException, IOException, InterruptedException {
        List<String> url = List.of("/events/" + dto.getId());
        List<ViewStatsDto> statsViewDto = getStats(url);
        Long view = (statsViewDto.size() != 0) ? statsViewDto.get(0).getHits() : 0L;
        dto.setViews(view);
        addHit(req, url);
        return dto;
    }

    @Override
    public List<EventShortDto> getView(HttpServletRequest req, List<EventShortDto> dtos) throws URISyntaxException, IOException, InterruptedException {
        List<String> url = dtos.stream()
                .map(el -> "/events/" + el.getId())
                .collect(Collectors.toList());
        List<ViewStatsDto> statsViewDtoList = getStats(url);
        Map<String, ViewStatsDto> statsViewDtoMap = statsViewDtoList.stream().collect(Collectors.toMap(ViewStatsDto::getUrl, v -> v));
        dtos.forEach(el -> {
            String key = "/events/" + el.getId();
            ViewStatsDto stats = new ViewStatsDto();
            Long hits = statsViewDtoMap.getOrDefault(key, stats).getHits();
            el.setViews(hits);
        });
        addHit(req, url);
        return dtos;
    }

    ;

    private void sendHit(EndpointHitDto endpointHitDto) throws JsonProcessingException, URISyntaxException {
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(endpointHitDto);

        HttpRequest statRequest = HttpRequest.newBuilder(new URI(serverUrl + "/hit"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient.newHttpClient()
                .sendAsync(statRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode);
    }

    private List<ViewStatsDto> getStats(List<String> url) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder(serverUrl + "/stats")
                .addParameter("start", LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("end", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("url", String.join(",", url))
                .build();

        HttpRequest statRequest = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(statRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

}
