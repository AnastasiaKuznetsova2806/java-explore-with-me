package ewmmainservice.rating.service;

import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.user.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {
    void addEventRating(long userId, long eventId, boolean rating);

    void deleteEventRating(long userId, long eventId);

    List<EventFullDto> findRatingEvents(boolean like, Pageable pageable);

    List<UserDto> findRatingUsers(boolean like, Pageable pageable);
}
