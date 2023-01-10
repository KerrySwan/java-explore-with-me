package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String email;
    String name;


}
