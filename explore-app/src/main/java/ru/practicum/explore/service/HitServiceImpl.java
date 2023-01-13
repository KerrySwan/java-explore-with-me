package ru.practicum.explore.service;

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
    public void addHit(HttpServletRequest req) throws URISyntaxException, IOException, InterruptedException {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .app("explore-main-service")
                .uri(req.getRequestURL().toString())
                .timestamp(LocalDateTime.now())
                .ip(req.getRemoteAddr())
                .build();
        sendHit(endpointHitDto);
    }

    private void addHit(HttpServletRequest request, List<String> url) throws IOException, URISyntaxException, InterruptedException {
        List<EndpointHitDto> statsHitDtoList = url.stream()
                .map(uri -> EndpointHitDto.builder()
                        .app("explore-main-service")
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
        addHit(req, url);
        List<ViewStatsDto> statsViewDto = getStats(url);
        Long view = (statsViewDto.size() != 0) ? statsViewDto.get(0).getHits() : 0L;
        dto.setViews(view);
        return dto;
    }

    @Override
    public List<EventShortDto> getView(HttpServletRequest req, List<EventShortDto> dtos) throws URISyntaxException, IOException, InterruptedException {
        List<String> url = dtos.stream()
                .map(el -> "/events/" + el.getId())
                .collect(Collectors.toList());
        addHit(req, url);
        List<ViewStatsDto> statsViewDtoList = getStats(url);
        Map<String, ViewStatsDto> statsViewDtoMap = statsViewDtoList.stream().collect(Collectors.toMap(ViewStatsDto::getUri, v -> v));
        dtos.forEach(el -> {
            String key = "/events/" + el.getId();
            ViewStatsDto stats = new ViewStatsDto();
            Long hits = statsViewDtoMap.getOrDefault(key, stats).getHits();
            el.setViews(hits);
        });
        return dtos;
    }

    ;

    private void sendHit(EndpointHitDto endpointHitDto) throws IOException, URISyntaxException, InterruptedException {
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(endpointHitDto);

        HttpRequest statRequest = HttpRequest.newBuilder(new URI(serverUrl + "/hit"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient.newHttpClient()
                .send(statRequest, HttpResponse.BodyHandlers.ofString());
        Thread.sleep(1000);
    }

    private List<ViewStatsDto> getStats(List<String> uris) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder(serverUrl + "/stats")
                .addParameter("start", LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("end", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("uris", String.join(",", uris))
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
