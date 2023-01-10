package ru.practicum.stat.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.commons.dto.EndpointHitDto;
import ru.practicum.stat.commons.dto.ViewStatsDto;
import ru.practicum.stat.service.HitService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class HitController {

    private HitService hitService;

    @PostMapping("/hit")
    public void hit(@Valid @RequestBody EndpointHitDto statsHitDto) {
        hitService.addEndpointHit(statsHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uri, 
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        return hitService.getView(start, end, uri, unique);
    }

}
