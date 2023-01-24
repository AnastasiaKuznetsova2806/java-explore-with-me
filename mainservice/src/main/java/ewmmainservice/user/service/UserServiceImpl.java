package ewmmainservice.user.service;

import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ewmmainservice.user.UserMapper;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.dto.UserShortDto;
import ewmmainservice.user.model.User;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final CheckDataValidation validation;

    @Override
    public UserDto createUser(UserShortDto userShortDto) {
        validation.userCheck(userShortDto);
        User user = UserMapper.toUser(userShortDto);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public List<UserDto> findAllUsers(List<Long> ids, Pageable pageable) {
        if (ids == null) {
            return repository.findAll(pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return repository.findAllById(ids).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deleteUserById(long userId) {
        findUserById(userId);
        repository.deleteById(userId);
    }

    @Override
    public UserDto findUserById(long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Пользователь %d не найден", userId)));
        return UserMapper.toUserDto(user);
    }
}
