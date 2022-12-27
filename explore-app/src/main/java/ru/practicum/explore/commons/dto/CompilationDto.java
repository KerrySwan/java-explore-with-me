package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CompilationDto {

    long id;
    List<Long> events;
    boolean pinned;
    String title;

}
