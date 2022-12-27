package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "compilation")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne()
    @JoinColumn(
            name = "event_id",
            referencedColumnName = "id"
    )
    Event event;
    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
    boolean pinned;
    String title;


}
