package ewmmainservice.user.service;

import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.dto.UserShortDto;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    UserDto createUser(UserShortDto userShortDto);

    List<UserDto> findAllUsers(List<Long> ids, Pageable pageable);

    void deleteUserById(long userId);


    UserDto findUserById(long userId);
}
