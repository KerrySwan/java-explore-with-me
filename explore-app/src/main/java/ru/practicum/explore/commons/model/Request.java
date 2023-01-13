package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "request")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
    @ManyToOne()
    @JoinColumn(
            name = "event_id",
            referencedColumnName = "id"
    )
    Event event;
    @ManyToOne()
    @JoinColumn(
            name = "status_id",
            referencedColumnName = "id"
    )
    RequestStatus status;
    @Column(name = "created_on")
    LocalDateTime createdOn;

}
