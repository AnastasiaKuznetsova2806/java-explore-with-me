package ewmmainservice.request.service;

import ewmmainservice.category.model.Category;
import ewmmainservice.common.exception.ConflictException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.event.EventMapper;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.location.model.Location;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.service.EventService;
import ewmmainservice.event.util.State;
import ewmmainservice.request.RequestMapper;
import ewmmainservice.request.Status;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.model.Request;
import ewmmainservice.request.repository.RequestRepository;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.model.User;
import ewmmainservice.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RequestServiceImplTest {
    @Mock
    private RequestService service;
    @Mock
    private RequestRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private EventService eventService;
    @InjectMocks
    private CheckDataValidation validation;
    private Request request;
    private User user;
    private EventFullDto eventFullDto;

    @BeforeEach
    void setUp() {
        repository = mock(RequestRepository.class);
        service = new RequestServiceImpl(repository, userService, eventService, validation);
        setParam();
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        ParticipationRequestDto requestDto = new ParticipationRequestDto(
                dateTime, 1L, 1L, 2L, Status.PENDING
        );
        request = repository.save(RequestMapper.toRequest(requestDto, user, eventFullDto));
    }

    @Test
    void createRequestConflictExceptionParticipationInYourEvent() {
        when(userService.findUserById(anyLong())).thenReturn(UserMapper.toUserDto(user));
        when(eventService.findEventById(anyLong(), anyBoolean())).thenReturn(eventFullDto);
        when(repository.save(any())).thenThrow(
                new ConflictException("Инициатор события не может добавить запрос на участие в своём событии")
        );

        final ConflictException exception = assertThrows(
                ConflictException.class,
                () -> service.createRequest(1L, 1L)
        );

        assertEquals("Инициатор события не может добавить запрос на участие в своём событии",
                exception.getMassage());
    }

    private void setParam() {
        user = new User(1L, "user", "user@user.com");
        Category category = new Category(1L, "Category");
        Location location = new Location(1L, 53.8308f, -89.5691f);
        Event event = new Event(
                1L,
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
        );
        eventFullDto = EventMapper.toEventFullDto(event);
    }
}