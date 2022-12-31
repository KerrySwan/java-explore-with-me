package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "request_status")
@NoArgsConstructor
@Table(name = "request_status")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

}
