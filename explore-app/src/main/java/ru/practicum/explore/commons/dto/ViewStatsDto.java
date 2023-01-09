package ru.practicum.explore.commons.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewStatsDto {

    private String app;
    private String url;
    private Long hits = 0L;

}
