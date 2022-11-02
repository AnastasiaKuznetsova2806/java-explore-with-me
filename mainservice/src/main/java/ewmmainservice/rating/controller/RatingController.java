package ewmmainservice.rating.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.rating.service.RatingService;
import ewmmainservice.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rating")
public class RatingController {
    private final RatingService service;

    @GetMapping(value = "/events")
    public List<EventFullDto> findRatingEvents(@RequestParam(defaultValue = "true") boolean like,
                                               @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                               @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение рейтинга мероприятий: " +
                        "like='{}', from={}, size={}", like, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findRatingEvents(like, pageable);
    }

    @GetMapping(value = "/users")
    public List<UserDto> findRatingUsers(@RequestParam(defaultValue = "true") boolean like,
                                         @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                         @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение рейтинга авторов: " +
                "like='{}', from={}, size={}", like, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findRatingUsers(like, pageable);
    }
}
