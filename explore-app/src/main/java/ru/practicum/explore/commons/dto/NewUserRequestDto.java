package ru.practicum.explore.commons.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequestDto {

    String email;
    @NotBlank
    String name;

}
