package ewmmainservice.event;

import ewmmainservice.category.CategoryMapper;
import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.category.model.Category;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.dto.EventShortDto;
import ewmmainservice.event.dto.NewEventDto;
import ewmmainservice.event.location.LocationMapper;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.util.DataConverter;
import ewmmainservice.event.util.State;
import lombok.experimental.UtilityClass;
import ewmmainservice.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {

    public static Event toEvent(EventFullDto eventFullDto, User user) {
        return new Event(
                eventFullDto.getId(),
                eventFullDto.getAnnotation(),
                new Category(eventFullDto.getCategory().getId(), eventFullDto.getCategory().getName()),
                eventFullDto.getDescription(),
                DataConverter.toDate(eventFullDto.getEventDate()),
                DataConverter.toDate(eventFullDto.getCreatedOn()),
                user,
                LocationMapper.toLocation(eventFullDto.getLocation()),
                eventFullDto.isPaid(),
                eventFullDto.getParticipantLimit(),
                DataConverter.toDate(eventFullDto.getPublishedOn()),
                eventFullDto.isRequestModeration(),
                eventFullDto.getState(),
                eventFullDto.getTitle(),
                eventFullDto.getViews()
        );
    }

    public static Event toEvent(NewEventDto eventDto, User user) {
        return new Event(
                0,
                eventDto.getAnnotation(),
                CategoryMapper.toCategory(eventDto.getCategory()),
                eventDto.getDescription(),
                DataConverter.toDate(eventDto.getEventDate()),
                LocalDateTime.now(),
                user,
                LocationMapper.toLocation(eventDto.getLocation()),
                eventDto.isPaid(),
                eventDto.getParticipantLimit(),
                null,
                eventDto.isRequestModeration(),
                State.PENDING,
                eventDto.getTitle(),
                0
        );
    }

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                0,
                DataConverter.toDateString(event.getCreatedOn()),
                event.getDescription(),
                DataConverter.toDateString(event.getEventDate()),
                event.getId(),
                new EventFullDto.InitiatorDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()
                ),
                LocationMapper.toLocationDto(event.getLocation()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? DataConverter.toDateString(event.getPublishedOn()) : null,
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static EventFullDto toEventFullDto(Event event, long confirmedRequests) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests,
                DataConverter.toDateString(event.getCreatedOn()),
                event.getDescription(),
                DataConverter.toDateString(event.getEventDate()),
                event.getId(),
                new EventFullDto.InitiatorDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()
                ),
                LocationMapper.toLocationDto(event.getLocation()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? DataConverter.toDateString(event.getPublishedOn()) : null,
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static EventShortDto toEventShortDto(EventFullDto eventFullDto) {
        return new EventShortDto(
                eventFullDto.getAnnotation(),
                eventFullDto.getCategory(),
                eventFullDto.getConfirmedRequests(),
                eventFullDto.getEventDate(),
                eventFullDto.getId(),
                new EventShortDto.InitiatorDto(
                        eventFullDto.getInitiator().getId(),
                        eventFullDto.getInitiator().getName()
                ),
                eventFullDto.isPaid(),
                eventFullDto.getTitle(),
                eventFullDto.getViews()
        );
    }
}
