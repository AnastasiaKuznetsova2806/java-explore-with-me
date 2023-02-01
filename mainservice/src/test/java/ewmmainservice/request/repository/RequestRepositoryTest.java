package ewmmainservice.request.repository;

import ewmmainservice.category.model.Category;
import ewmmainservice.category.repository.CategoryRepository;
import ewmmainservice.event.EventMapper;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.location.LocationMapper;
import ewmmainservice.event.location.dto.LocationDto;
import ewmmainservice.event.location.model.Location;
import ewmmainservice.event.location.repository.LocationRepository;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.repository.EventRepository;
import ewmmainservice.event.util.State;
import ewmmainservice.request.RequestMapper;
import ewmmainservice.request.Status;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.model.Request;
import ewmmainservice.user.model.User;
import ewmmainservice.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RequestRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RequestRepository repository;
    private User user;
    private EventFullDto eventFullDto;
    private Request request;

    @BeforeEach
    void setUp() {
        setParam();
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        ParticipationRequestDto requestDto = new ParticipationRequestDto(
                dateTime, 1L, 1L, 1L, Status.PENDING
        );
        request = repository.save(RequestMapper.toRequest(requestDto, user, eventFullDto));
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        repository.deleteAll();
        eventRepository.deleteAll();
    }

    @Test
    void findAllByRequesterIdAndEventId() {
        Request result = repository.findAllByRequesterIdAndEventId(user.getId(), eventFullDto.getId());

        assertThat(result, notNullValue());
        assertEquals(1, result.getId());
        assertEquals(request.getEvent().getId(), result.getEvent().getId());
    }

    private void setParam() {
        user = userRepository.save(new User(1L, "user", "user@user.com"));
        Category category = categoryRepository.save(new Category(1L, "Category"));
        Location location = locationRepository.save(
                LocationMapper.toLocation(new LocationDto(53.8308f, -89.5691f)
                ));
        Event event = eventRepository.save(new Event(
                0L,
                "annotation",
                category,
                "descriptionNewEventDto",
                LocalDateTime.now().plusDays(20).withNano(0),
                LocalDateTime.now().withNano(0),
                user,
                location,
                true,
                289,
                LocalDateTime.now().withNano(0),
                true,
                State.PUBLISHED,
                "title",
                0
        ));
        eventFullDto = EventMapper.toEventFullDto(event);
    }
}