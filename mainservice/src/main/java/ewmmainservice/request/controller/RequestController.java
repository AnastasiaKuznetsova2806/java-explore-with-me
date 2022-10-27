package ewmmainservice.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users/{userId}/requests")
public class RequestController {
    private final RequestService service;

    @PostMapping
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                 @RequestParam long eventId) {
        log.info("Получен запрос на добавление запроса: eventId={} от userId={}", eventId, userId);
        return service.createRequest(userId, eventId);
    }

    @PatchMapping(value = "/{requestId}/cancel")
    public ParticipationRequestDto updateRequest(@PathVariable long userId,
                                                 @PathVariable long requestId) {
        log.info("Получен запрос на обновление запроса: requestId={} от userId={}", requestId, userId);
        return service.updateRequest(userId, requestId);
    }

    @GetMapping
    public List<ParticipationRequestDto> findAllRequestsByUserId(@PathVariable long userId) {
        log.info("Получен запрос на получение информации заявках: userId={} на участие в чужих событиях", userId);
        return service.findAllRequestsByUserId(userId);
    }
}
