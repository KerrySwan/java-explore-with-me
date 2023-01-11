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

    long id;
    List<Long> events;
    boolean pinned;
    @NotBlank
    String title;

}
