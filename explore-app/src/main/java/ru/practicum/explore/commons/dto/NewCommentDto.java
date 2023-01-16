package ru.practicum.explore.commons.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class NewCommentDto {

    @NotBlank
    private String text;

}
