package ru.practicum.explore.commons.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequestDto {

    String email;
    String name;

}
