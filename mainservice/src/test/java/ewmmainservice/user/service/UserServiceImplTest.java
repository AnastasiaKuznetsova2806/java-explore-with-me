package ewmmainservice.user.service;

import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.dto.UserShortDto;
import ewmmainservice.user.model.User;
import ewmmainservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceImplTest {
    private final UserShortDto userShortDto = new UserShortDto("user@user.com", "user");
    private final User user = UserMapper.toUser(userShortDto);
    private final UserDto userDto = UserMapper.toUserDto(user);
    @Mock
    private UserService service;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private CheckDataValidation validation;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(repository, validation);
    }

    @Test
    void createUser() {
        when(repository.save(user)).thenReturn(user);
        UserDto userResult = service.createUser(userShortDto);

        assertThat(userResult.getId(), notNullValue());
        assertThat(userResult.getName(), equalTo(userDto.getName()));
        assertThat(userResult.getEmail(), equalTo(userDto.getEmail()));
    }

    @Test
    void deleteUserById() {
        when(repository.save(user)).thenReturn(user);
        doNothing().when(repository).deleteById(anyLong());

        final DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> service.findUserById(1L)
        );

        assertEquals(exception.getMassage(), "Пользователь 1 не найден");
    }

    @Test
    void findUserById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDto userResult = service.findUserById(1L);

        assertThat(userResult.getId(), notNullValue());
        assertThat(userResult.getName(), equalTo(userResult.getName()));
        assertThat(userResult.getEmail(), equalTo(userResult.getEmail()));
    }
}