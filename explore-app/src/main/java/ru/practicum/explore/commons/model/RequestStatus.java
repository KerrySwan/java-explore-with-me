package ru.practicum.explore.commons.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    Long id;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStatus that = (RequestStatus) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
