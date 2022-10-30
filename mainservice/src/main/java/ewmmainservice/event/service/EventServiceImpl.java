package ewmmainservice.event.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import ewmmainservice.category.CategoryMapper;
import ewmmainservice.category.model.Category;
import ewmmainservice.category.service.CategoryService;
import ewmmainservice.client.dto.HitDto;
import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.event.EventMapper;
import ewmmainservice.client.EventClient;
import ewmmainservice.event.dto.*;
import ewmmainservice.event.location.model.Location;
import ewmmainservice.event.location.service.LocationService;
import ewmmainservice.event.model.QEvent;
import ewmmainservice.event.util.*;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.model.User;
import ewmmainservice.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final UserService userService;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final CheckDataValidation validation;
    private final EventClient client;

    @Override
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        validation.eventCheck(newEventDto);
        User user = checkUser(userId);

        LocalDateTime eventDate = DataConverter.toDate(newEventDto.getEventDate());
        validation.checkEventDate(eventDate, CommonConstant.PLUS_HOURS_PRIVATE);
        Event event = EventMapper.toEvent(newEventDto, user);

        long categoryId = newEventDto.getCategory().getId();
        Category category = getCategory(categoryId);
        event.setCategory(category);

        Location location = locationService.createLocation(event.getLocation());
        event.setLocation(location);
        return EventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest updateEvent) {
        checkUser(userId);
        validation.eventCheck(updateEvent);

        LocalDateTime eventDate = DataConverter.toDate(updateEvent.getEventDate());
        validation.checkEventDate(eventDate, CommonConstant.PLUS_HOURS_PRIVATE);

        Event oldEvent = getEvent(updateEvent.getEventId());
        validation.checkEventBeforeUpdate(oldEvent, userId);

        Event event = repository.save(getNewEvent(updateEvent, oldEvent));
        return findEventById(event.getId(), false);
    }

    @Override
    public EventFullDto updateEventCancellation(long userId, long eventId) {
        checkUser(userId);
        Event updateEvent = getEvent(eventId);
        validation.checkEventBeforeUpdate(updateEvent, userId);

        updateEvent.setState(State.CANCELED);
        repository.save(updateEvent);
        return findEventById(eventId, false);
    }

    @Override
    public List<EventFullDto> findAllEventsByUserId(long userId, Pageable pageable) {
        checkUser(userId);
        return repository.findAllByInitiatorId(userId, pageable).stream()
                .map(event -> findEventById(event.getId(), false))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findEventByIdAddedCurrentUser(long userId, long eventId) {
        checkUser(userId);
        Event event = repository.findByIdAndInitiatorId(eventId, userId);
        return findEventById(event.getId(), false);
    }

    @Override
    public EventFullDto findEventById(long eventId, boolean flagAddViews) {
        Event event = getEvent(eventId);

        if (flagAddViews) {
            addView(event);
        }

        long confirmedRequests = repository.countConfirmedRequests(eventId);
        return EventMapper.toEventFullDto(event, confirmedRequests);
    }

    @Override
    public List<EventFullDto> findAllEventsAdmin(Filter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getUsers(), QEvent.event.initiator.id::in)
                .add(filter.getStates(), QEvent.event.state::in)
                .add(filter.getCategories(), QEvent.event.category.id::in)
                .add(filter.getRangeStart(), QEvent.event.eventDate::goe)
                .add(filter.getRangeEnd(), QEvent.event.eventDate::loe)
                .buildAnd();
        return repository.findAll(predicate, pageable).stream()
                .map(event -> findEventById(event.getId(), false))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventAdmin(long eventId, AdminUpdateEventRequest updateEvent) {
        Event oldEvent = getEvent(eventId);
        long categoryId = updateEvent.getCategory().getId();
        LocalDateTime eventDate = DataConverter.toDate(updateEvent.getEventDate());

        Event newEvent = new Event(
                eventId,
                updateEvent.getAnnotation() != null ? updateEvent.getAnnotation() : oldEvent.getAnnotation(),
                updateEvent.getCategory() != null ? getCategory(categoryId) : oldEvent.getCategory(),
                updateEvent.getDescription() != null ? updateEvent.getDescription() : oldEvent.getDescription(),
                updateEvent.getEventDate() != null ? eventDate : oldEvent.getEventDate(),
                LocalDateTime.now(),
                oldEvent.getInitiator(),
                oldEvent.getLocation(),
                updateEvent.getPaid() != null ? updateEvent.getPaid() : oldEvent.isPaid(),
                updateEvent.getParticipantLimit() != null ?
                        updateEvent.getParticipantLimit() : oldEvent.getParticipantLimit(),
                oldEvent.getPublishedOn(),
                oldEvent.isRequestModeration(),
                State.CANCELED.equals(oldEvent.getState()) ? State.PENDING : oldEvent.getState(),
                updateEvent.getTitle() != null ? updateEvent.getTitle() : oldEvent.getTitle(),
                oldEvent.getViews()
        );
        repository.save(newEvent);
        return findEventById(eventId, false);
    }

    @Override
    public EventFullDto publishingEventsAdmin(long eventId) {
        Event updateEvent = getEvent(eventId);
        validation.checkEventDate(updateEvent.getEventDate(), CommonConstant.PLUS_HOURS_ADMIN);
        validation.checkEventStatePending(updateEvent);

        updateEvent.setPublishedOn(LocalDateTime.now());
        updateEvent.setState(State.PUBLISHED);

        repository.save(updateEvent);
        return findEventById(eventId, false);
    }

    @Override
    public EventFullDto rejectionEventAdmin(long eventId) {
        Event updateEvent = getEvent(eventId);
        validation.checkEventStatePublished(updateEvent.getState());

        updateEvent.setState(State.CANCELED);

        repository.save(updateEvent);
        return findEventById(eventId, false);
    }

    @Override
    public List<EventFullDto> findAllEventPublic(HttpServletRequest request, Filter filter, Pageable pageable) {
        saveInfoStatisticService(request);

        Predicate predicate = getPredicates(filter);
        return repository.findAll(predicate, pageable).stream()
                .map(event -> findEventById(event.getId(), true))
                .filter(event -> filterLimit(filter.isOnlyAvailable(), event))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findEventByIdPublic(HttpServletRequest request, long id) {
        saveInfoStatisticService(request);
        Event event = repository.findByIdAndState(id, State.PUBLISHED);
        return findEventById(event.getId(), true);
    }

    @Override
    public List<Event> getAllEventsById(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Event getEvent(long eventId) {
        return repository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Событие %d не найдено", eventId)));
    }

    private void addView(Event event) {
        event.setViews(event.getViews() + 1);
        repository.save(event);
    }

    private User checkUser(long userId) {
        UserDto userDto = userService.findUserById(userId);
        return UserMapper.toUser(userDto);
    }

    private Event getNewEvent(UpdateEventRequest updateEvent, Event oldEvent) {
        long categoryId = updateEvent.getCategory().getId();
        LocalDateTime eventDate = DataConverter.toDate(updateEvent.getEventDate());

        return new Event(
                updateEvent.getEventId(),
                updateEvent.getAnnotation() != null ? updateEvent.getAnnotation() : oldEvent.getAnnotation(),
                updateEvent.getCategory() != null ? getCategory(categoryId) : oldEvent.getCategory(),
                updateEvent.getDescription() != null ? updateEvent.getDescription() : oldEvent.getDescription(),
                updateEvent.getEventDate() != null ? eventDate : oldEvent.getEventDate(),
                LocalDateTime.now(),
                oldEvent.getInitiator(),
                oldEvent.getLocation(),
                updateEvent.getPaid() != null ? updateEvent.getPaid() : oldEvent.isPaid(),
                updateEvent.getParticipantLimit() != null ?
                        updateEvent.getParticipantLimit() : oldEvent.getParticipantLimit(),
                oldEvent.getPublishedOn(),
                oldEvent.isRequestModeration(),
                oldEvent.getState().equals(State.CANCELED) ? State.PENDING : oldEvent.getState(),
                updateEvent.getTitle() != null ? updateEvent.getTitle() : oldEvent.getTitle(),
                oldEvent.getViews()
        );
    }

    private Category getCategory(long categoryId) {
        return CategoryMapper.toCategory(
                categoryService.findCategoryById(categoryId)
        );
    }

    private Predicate getPredicates(Filter filter) {
        LocalDateTime timeNow = checkDate(filter);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QPredicates.builder()
                .add(filter.getText(), QEvent.event.annotation::likeIgnoreCase)
                .add(filter.getText(), QEvent.event.description::likeIgnoreCase)
                .buildOr());
        predicates.add(QPredicates.builder()
                .add(filter.getCategories(), QEvent.event.category.id::in)
                .add(filter.isPaid(), QEvent.event.paid::eq)
                .add(timeNow, QEvent.event.eventDate::goe)
                .add(filter.getRangeEnd(), QEvent.event.eventDate::loe)
                .add(State.PUBLISHED, QEvent.event.state::eq)
                .buildAnd());

        return ExpressionUtils.allOf(predicates);
    }

    private LocalDateTime checkDate(Filter filter) {
        if (filter.getRangeStart() == null ||
                filter.getRangeEnd() == null) {
            return LocalDateTime.now();
        } else {
            return filter.getRangeStart();
        }
    }

    private boolean filterLimit(boolean onlyAvailable, EventFullDto event) {
        if (onlyAvailable) {
            return event.getParticipantLimit() < event.getConfirmedRequests();
        } else {
            return true;
        }
    }

    private void saveInfoStatisticService(HttpServletRequest request) {
        HitDto hitDto = new HitDto(
                0,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                DataConverter.toDateString(LocalDateTime.now())

        );
        client.createHit(hitDto);
    }
}
