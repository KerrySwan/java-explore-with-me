package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class NewCompilationDto {

    List<Long> events;
    boolean pinned;
    String title;

}
