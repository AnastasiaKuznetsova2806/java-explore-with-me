package ewmmainservice.event.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import ewmmainservice.event.util.DataConverter;
import ewmmainservice.event.util.Filter;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/events")
public class EventPublicController {
    private final EventService service;

    @GetMapping
    public List<EventFullDto> findAllEventPublic(@RequestParam(required = false) String text,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false) boolean paid,
                                           @RequestParam(required = false) String rangeStart,
                                           @RequestParam(required = false) String rangeEnd,
                                           @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                           @RequestParam(defaultValue = CommonConstant.DEFAULT_SORT) String sort,
                                           @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                           @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size,
                                           HttpServletRequest request) {
        log.info("Получен запрос на получение событий с возможностью фильтрации: " +
                "text='{}', categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, " +
                "sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size, sort);
        Filter filtration = new Filter(
                text,
                categories,
                paid,
                DataConverter.toDate(rangeStart),
                DataConverter.toDate(rangeEnd),
                onlyAvailable
        );

        return service.findAllEventPublic(request, filtration, pageable);
    }

    @GetMapping(value = "/{id}")
    public EventFullDto findEventByIdPublic(@PathVariable long id,
                                            HttpServletRequest request) {
        log.info("Получен запрос на получение подробной информации об опубликованном событии id={}", id);
        return service.findEventByIdPublic(request, id);
    }
}
