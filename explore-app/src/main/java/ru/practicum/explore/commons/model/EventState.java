package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "event_state")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EventState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

    public EventState(String name) {
        this.name = name;
    }
}
