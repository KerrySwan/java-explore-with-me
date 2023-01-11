package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    @Column(name = "confirmed_requests")
    long confirmedRequests;
    @Column(name = "created_on")
    LocalDateTime createdOn;
    String description;
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
    @Column(name = "location_lat")
    float locationLat;
    @Column(name = "location_lon")
    float locationLon;
    boolean paid;
    @Column(name = "participant_limit")
    long participantLimit;
    @Column(name = "published_on")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    boolean requestModeration;
    @ManyToOne
    @JoinColumn(
            name = "state_id",
            referencedColumnName = "id"
    )
    EventState state;
    String title;

}
