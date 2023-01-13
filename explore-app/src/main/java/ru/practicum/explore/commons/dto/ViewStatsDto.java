package ru.practicum.explore.commons.dto;

import lombok.*;


@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDto {

    private String app;
    private String uri;
    private Long hits = 0L;

}
