package ru.practicum.explore.commons.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    public List<Long> getEvents() {
        return events == null ? Collections.emptyList() : events;
    }

    List<Long> events;
    boolean pinned;
    @NotBlank
    String title;


}
