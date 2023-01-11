package ru.practicum.explore.commons.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    long id;
    String email;
    String name;

}
