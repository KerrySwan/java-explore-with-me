package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "event")
@NoArgsConstructor
@Table(name = "event")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String annotation;
    @ManyToOne()
    @JoinColumn(
        name = "category_id",
        referencedColumnName = "id"
    )
    Category category;
    long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
    float locationLat;
    float locationLon;
    boolean paid;
    long participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    @JoinColumn(
            name = "state_id",
            referencedColumnName = "id"
    )
    EventState state;
    String title;

}
