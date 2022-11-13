package ewmmainservice.rating.service;

import ewmmainservice.common.exception.ConflictException;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.service.EventService;
import ewmmainservice.rating.model.Rating;
import ewmmainservice.rating.model.RatingKey;
import ewmmainservice.rating.repository.RatingRepository;
import ewmmainservice.request.service.RequestService;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.model.User;
import ewmmainservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository repository;
    private final UserService userService;
    private final EventService eventService;
    private final RequestService requestService;

    @Override
    public void addEventRating(long userId, long eventId, boolean rating) {
        User user = UserMapper.toUser(userService.findUserById(userId));
        Event event = eventService.getEvent(eventId);
        checkUserForParticipationInEvent(userId, eventId);

        repository.save(
                new Rating(event, user, rating)
        );
    }

    @Override
    public void deleteEventRating(long userId, long eventId) {
        userService.findUserById(userId);
        eventService.getEvent(eventId);
        repository.deleteById(
                new RatingKey(eventId, userId)
        );
    }

    @Override
    public List<EventFullDto> findRatingEvents(boolean like, Pageable pageable) {
        return repository.findAllRating(like, pageable).stream()
                .map(eventId -> eventService.findEventById(eventId, false))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findRatingUsers(boolean like, Pageable pageable) {
        return findRatingEvents(like, pageable).stream()
                .map(eventFullDto -> userService.findUserById(eventFullDto.getInitiator().getId()))
                .collect(Collectors.toList());
    }

    private void checkUserForParticipationInEvent(long userId, long eventId) {
        if (requestService.getRequestByUserIdAndEventId(userId, eventId) == null) {
            throw new ConflictException("Лайкать/дизлайкать можно только посещённые события");
        }
    }
}
