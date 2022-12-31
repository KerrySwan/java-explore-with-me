package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.EventShortDto;
import ru.practicum.explore.commons.dto.NewEventDto;
import ru.practicum.explore.commons.dto.UpdateEventRequestDto;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.Location;

public class EventMapper {

    public static Event toModel(EventFullDto dto){
        return Event.builder()
                .id(dto.getId())
                .annotation(dto.getAnnotation())
                .category(dto.getCategory())
                .confirmedRequests(dto.getConfirmedRequests())
                .createdOn(dto.getCreatedOn())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .user(dto.getInitiator())
                .locationLat(dto.getLocation().getLat())
                .locationLon(dto.getLocation().getLon())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .publishedOn(dto.getPublishedOn())
                .requestModeration(dto.isRequestModeration())
                .state(dto.getState())
                .title(dto.getTitle())
                .build();
    };

    public static Event toModel(EventShortDto dto){
        return Event.builder()
                .id(dto.getId())
                .annotation(dto.getAnnotation())
                .category(dto.getCategory())
                .confirmedRequests(dto.getConfirmedRequests())
                .eventDate(dto.getEventDate())
                .user(dto.getInitiator())
                .paid(dto.isPaid())
                .title(dto.getTitle())
                .build();
    };

    public static Event toModel(NewEventDto dto){
        return Event.builder()
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .locationLat(dto.getLocation().getLat())
                .locationLon(dto.getLocation().getLon())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    };

    public static Event toModel(UpdateEventRequestDto dto){
        return Event.builder()
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .title(dto.getTitle())
                .build();
    }

    public static EventFullDto toFullDto(Event model){
        return EventFullDto.builder()
                .id(model.getId())
                .annotation(model.getAnnotation())
                .location(new Location(model.getLocationLon(), model.getLocationLat()))
                .category(model.getCategory())
                .confirmedRequests(model.getConfirmedRequests())
                .createdOn(model.getCreatedOn())
                .description(model.getDescription())
                .eventDate(model.getEventDate())
                .user(model.getUser().getId())
                .paid(model.isPaid())
                .participantLimit(model.getParticipantLimit())
                .publishedOn(model.getPublishedOn())
                .requestModeration(model.isRequestModeration())
                .state(model.getState())
                .title(model.getTitle())
                .build();
    }

    public static EventShortDto toShortDto(Event model){
        return null;
    }

}
