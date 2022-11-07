package ewmmainservice.event.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.dto.NewEventDto;
import ewmmainservice.event.dto.UpdateEventRequest;
import ewmmainservice.event.service.EventService;
import ewmmainservice.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users/{userId}/events")
public class EventPrivateController {
    private final EventService service;
    private final RequestService requestService;
    private final RatingService ratingService;

    @PostMapping
    public EventFullDto createEvent(@PathVariable long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на добавление пользователем userId={} события: '{}' ", userId, newEventDto);
        return service.createEvent(userId, newEventDto);
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @Valid @RequestBody UpdateEventRequest updateEventRequest) {
        log.info(
                "Получен запрос на изменение события добавленного пользователем userId={} : '{}' ",
                userId, updateEventRequest
        );
        return service.updateEvent(userId, updateEventRequest);
    }

    @PatchMapping(value = "/{eventId}")
    public EventFullDto updateEventCancellation(@PathVariable long userId,
                                                @PathVariable long eventId) {
        log.info("Получен запрос на отмену события eventId={} добавленного пользователем userId={}",
                eventId, userId
        );
        return service.updateEventCancellation(userId, eventId);
    }

    @GetMapping
    public List<EventFullDto> findAllEventsByUserId(@PathVariable long userId,
                                      @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                      @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info(
                "Получен запрос на получение информации о событиях добавленных пользователем " +
                        "userId={}: from={}, size={}",userId, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findAllEventsByUserId(userId, pageable);
    }

    @GetMapping(value = "/{eventId}")
    public EventFullDto findEventByIdAddedCurrentUser(@PathVariable long userId,
                                                      @PathVariable long eventId) {
        log.info(
                "Получен запрос на получение информации о событии eventId={} добавленном пользователем " +
                        "userId={}",eventId, userId
        );
        return service.findEventByIdAddedCurrentUser(userId, eventId);
    }

    //requests
    @GetMapping(value = "/{eventId}/requests")
    public List<ParticipationRequestDto> findRequestsToParticipateInUsersEvent(@PathVariable long userId,
                                                                               @PathVariable long eventId) {
        log.info(
                "Получен запрос на получение информации о запросах на участие в событии eventId={}" +
                        " добавленном пользователем userId={}",eventId, userId
        );
        return requestService.findRequestsToParticipateInUsersEvent(userId, eventId);
    }

    @PatchMapping(value = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmationRequestParticipationInEvent(@PathVariable long userId,
                                                                           @PathVariable long eventId,
                                                                           @PathVariable long reqId) {
        log.info("Получен запрос на подтверждение заявки reqId={} на участие в событии eventId={} " +
                " пользователя userId={} ", reqId, eventId, userId
        );
        return requestService.confirmationRequestParticipationInEvent(userId, eventId, reqId);
    }

    @PatchMapping(value = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestParticipationInEvent(@PathVariable long userId,
                                                                     @PathVariable long eventId,
                                                                     @PathVariable long reqId) {
        log.info("Получен запрос на отклонение заявки reqId={} на участие в событии eventId={} " +
                " пользователя userId={} ", reqId, eventId, userId
        );
        return requestService.rejectRequestParticipationInEvent(userId, eventId, reqId);
    }

    @PostMapping(value = "/{eventId}/rating")
    public void addEventRating(@PathVariable long userId,
                               @PathVariable long eventId,
                               @RequestParam boolean rating) {
        log.info("Получен запрос на добавление рейтинга rating={} на событие eventId={} " +
                " пользователем userId={} ", rating, eventId, userId
        );
        ratingService.addEventRating(userId, eventId, rating);
    }

    @DeleteMapping(value = "/{eventId}/rating")
    public void deleteEventRating(@PathVariable long userId,
                                  @PathVariable long eventId) {
        log.info("Получен запрос на удаление рейтинга на событие eventId={} " +
                " пользователем userId={} ", eventId, userId
        );
        ratingService.deleteEventRating(userId, eventId);
    }
}
