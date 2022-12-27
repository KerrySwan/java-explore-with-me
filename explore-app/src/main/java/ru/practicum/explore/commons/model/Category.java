package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "category")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

}
