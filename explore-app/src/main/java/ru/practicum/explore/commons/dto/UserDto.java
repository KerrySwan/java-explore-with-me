package ru.practicum.explore.commons.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    long id;
    @NotBlank
    String email;
    @NotBlank
    String name;

}
