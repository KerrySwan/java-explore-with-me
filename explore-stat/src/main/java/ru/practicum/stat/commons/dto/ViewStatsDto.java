package ru.practicum.stat.commons.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewStatsDto {

    private String app;
    private String uri;
    private Long hits = 0L;

}
