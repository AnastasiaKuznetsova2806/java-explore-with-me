package ewmmainservice.request.service;

import ewmmainservice.common.exception.ConflictException;
import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ewmmainservice.request.RequestMapper;
import ewmmainservice.request.Status;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.model.Request;
import ewmmainservice.request.repository.RequestRepository;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.model.User;
import ewmmainservice.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository repository;
    private final UserService userService;
    private final EventService eventService;
    private final CheckDataValidation validation;

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        ParticipationRequestDto requestDto = checkRequest(userId, eventId);
        User user = checkUser(userId);
        EventFullDto event = checkEvent(eventId);
        validation.checkEventForRequestCreation(event, eventId);

        if (!event.isRequestModeration()) {
            requestDto.setStatus(Status.CONFIRMED);
        }

        Request request = RequestMapper.toRequest(requestDto, user, event);
        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    public ParticipationRequestDto updateRequest(long userId, long requestId) {
        checkUser(userId);
        Request request = findRequestById(requestId);

        if (request.getRequester().getId() == userId) {
            request.setStatus(Status.CANCELED);
        }
        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> findAllRequestsByUserId(long userId) {
        checkUser(userId);
        return repository.findAllByRequester_Id(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDto> findRequestsToParticipateInUsersEvent(long userId, long eventId) {
        checkUser(userId);
        checkEvent(eventId);
        return repository.findAllByEvent_Id(eventId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmationRequestParticipationInEvent(long userId, long eventId, long reqId) {
        checkUser(userId);
        Request requestParticipation = findRequestById(reqId);
        EventFullDto event = checkEvent(eventId);
        validation.checkEventBeforeConfirmRequest(event);

        requestParticipation.setStatus(Status.CONFIRMED);
        Request request = repository.save(requestParticipation);

        checkLimitConfirmRequests(event);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequestParticipationInEvent(long userId, long eventId, long reqId) {
        checkUser(userId);
        checkEvent(eventId);
        Request request = findRequestById(reqId);

        request.setStatus(Status.REJECTED);
        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    public long getConfirmedRequests(long eventId) {
        return repository.countConfirmedRequests(eventId, Status.CONFIRMED);
    }

    private Request findRequestById(long requestId) {
        if (repository.findById(requestId).isEmpty()) {
            throw new DataNotFoundException(String.format("Запрос %d не найден", requestId));
        }
        return repository.findById(requestId).get();
    }

    private ParticipationRequestDto checkRequest(long userId, long eventId) {
        if (repository.findAllByRequester_IdAndEvent_Id(userId, eventId) != null) {
            throw new ConflictException("Нельзя добавить повторный запрос");
        } else {
            return new ParticipationRequestDto(LocalDateTime.now(), eventId, 0, userId, Status.PENDING);
        }
    }

    private User checkUser(long userId) {
        UserDto userDto = userService.findUserById(userId);
        return UserMapper.toUser(userDto);
    }

    private EventFullDto checkEvent(long eventId) {
        return eventService.findEventById(eventId, false);
    }

    private void checkLimitConfirmRequests(EventFullDto event) {
        long limit = getConfirmedRequests(event.getId());
        if (limit >= event.getParticipantLimit()) {
            repository.updateRequestsByEventIdAndStatus(Status.CANCELED, event.getId(), Status.PENDING);
        }
    }
}
