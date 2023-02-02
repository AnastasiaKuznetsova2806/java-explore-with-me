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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
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
        assertEquals(request.getId(), result.getId());
        assertEquals(request.getEvent().getId(), result.getEvent().getId());
    }

    @Test
    void findAllByRequesterId() {
        List<Request> result = repository.findAllByRequesterId(user.getId());

        assertThat(result, notNullValue());
        assertThat(result, hasItem(request));
        assertEquals(request.getId(), result.get(0).getId());
    }

    @Test
    void findAllByEventId() {
        List<Request> result = repository.findAllByEventId(eventFullDto .getId());

        assertThat(result, notNullValue());
        assertThat(result, hasItem(request));
        assertEquals(request.getId(), result.get(0).getId());
    }

    @Test
    void countConfirmedRequests() {
        long result = repository.countConfirmedRequests(eventFullDto .getId(), Status.PENDING);

        assertThat(result, notNullValue());
        assertEquals(1, result);
    }

    @Test
    void updateRequestsByEventIdAndStatus() {
        repository.updateRequestsByEventIdAndStatus(Status.CANCELED, eventFullDto .getId(), Status.PENDING);
        List<Request> result = repository.findAllByEventId(eventFullDto .getId());

        assertThat(result, notNullValue());
        assertEquals(request.getId(), result.get(0).getId());
        assertEquals(request.getStatus(), result.get(0).getStatus());
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