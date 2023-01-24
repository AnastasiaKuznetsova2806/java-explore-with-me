package ewmmainservice.event.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import ewmmainservice.event.dto.AdminUpdateEventRequest;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.service.EventService;
import ewmmainservice.event.util.DataConverter;
import ewmmainservice.event.util.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/events")
public class EventAdminController {
    private final EventService service;

    @GetMapping
    public List<EventFullDto> findAllEventsAdmin(@RequestParam(required = false) List<Long> users,
                                                 @RequestParam(required = false) List<String> states,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                                 @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение событий с возможностью фильтрации: " +
                        "users='{}', states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size);
        Filter filtration = new Filter(
                users,
                DataConverter.toState(states),
                categories,
                DataConverter.toDate(rangeStart),
                DataConverter.toDate(rangeEnd)
        );
        return service.findAllEventsAdmin(filtration, pageable);
    }

    @PutMapping(value = "/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable long eventId,
                                         @RequestBody AdminUpdateEventRequest updateEventRequest) {
        log.info("Получен запрос на изменение события eventId={} добавленного администратором: '{}' ",
                eventId, updateEventRequest
        );
        return service.updateEventAdmin(eventId, updateEventRequest);
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishingEventAdmin(@PathVariable long eventId) {
        log.info("Получен запрос на публикацию события eventId={} ",eventId);
        return service.publishingEventsAdmin(eventId);
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectionEventAdmin(@PathVariable long eventId) {
        log.info("Получен запрос на отклонение события eventId={} ",eventId);
        return service.rejectionEventAdmin(eventId);
    }
}
