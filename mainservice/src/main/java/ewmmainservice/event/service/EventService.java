package ewmmainservice.event.service;

import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.dto.NewEventDto;
import ewmmainservice.event.dto.UpdateEventRequest;
import ewmmainservice.event.util.Filter;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.dto.AdminUpdateEventRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto updateEvent(long userId, UpdateEventRequest updateEventRequest);

    EventFullDto updateEventCancellation(long userId, long eventId);

    List<EventFullDto> findAllEventsByUserId(long userId, Pageable pageable);

    EventFullDto findEventByIdAddedCurrentUser(long userId, long eventId);

    EventFullDto findEventById(long eventId, boolean flagAddViews);

    List<EventFullDto> findAllEventsAdmin(Filter filtration, Pageable pageable);

    EventFullDto updateEventAdmin(long eventId, AdminUpdateEventRequest updateEventRequest);

    EventFullDto publishingEventsAdmin(long eventId);

    EventFullDto rejectionEventAdmin(long eventId);

    List<EventFullDto> findAllEventPublic(HttpServletRequest request, Filter filtration, Pageable pageable);

    EventFullDto findEventByIdPublic(HttpServletRequest request, long id);

    List<Event> getAllEventsById(List<Long> ids);

    Event getEvent(long eventId);
}
