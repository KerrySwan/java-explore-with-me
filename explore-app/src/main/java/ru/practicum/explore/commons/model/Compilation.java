package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(fetch = FetchType.EAGER)
    List<Event> events;
    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
    boolean pinned;
    String title;


}
