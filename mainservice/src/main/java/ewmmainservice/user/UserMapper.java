package ewmmainservice.user;

import lombok.experimental.UtilityClass;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.dto.UserShortDto;
import ewmmainservice.user.model.User;

@UtilityClass
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public static User toUser(UserShortDto userShortDto) {
        return new User(
                0,
                userShortDto.getName(),
                userShortDto.getEmail()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }
}