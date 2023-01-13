package ru.practicum.explore.commons.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    @NotBlank
    String title;
    long id;
    boolean pinned;
    List<EventShortDto> events;

}
