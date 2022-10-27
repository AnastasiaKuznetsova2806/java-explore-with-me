package ewmmainservice.user.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ewmmainservice.user.dto.UserDto;
import ewmmainservice.user.dto.UserShortDto;
import ewmmainservice.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserShortDto userShortDto) {
        log.info("Получен запрос на добавление пользователя: '{}' ", userShortDto);
        return service.createUser(userShortDto);
    }

    @GetMapping
    public List<UserDto> findAllUsers(@RequestParam List<Long> ids,
                                      @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                      @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение информации о пользователях: from={}, size={}", from, size);
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findAllUsers(ids, pageable);
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUserById(@PathVariable long userId) {
        log.info("Получен запрос на удаление пользователя: userId={} ", userId);
        service.deleteUserById(userId);
    }
}
