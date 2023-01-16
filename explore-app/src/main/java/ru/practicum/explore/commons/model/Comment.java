package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "comment")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "event_id",
            referencedColumnName = "id"
    )
    private Event event;
    private LocalDateTime date;

}
