package ewmmainservice.common.validation;

import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.common.exception.ConflictException;
import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.exception.ValidationException;
import ewmmainservice.compilation.dto.NewCompilationDto;
import ewmmainservice.event.util.State;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.event.model.Event;
import org.springframework.stereotype.Component;
import ewmmainservice.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Component
public class CheckDataValidation {
    public void userCheck(UserShortDto userDto) {
        if (userDto == null) {
            throw new DataNotFoundException("Пользователь не заполнен");
        }
    }

    public void categoryCheck(CategoryDto categoryDto) {
        if (categoryDto == null) {
                throw new DataNotFoundException("Категория не заполнена");
        }
    }

    public void checkEventForRequestCreation(EventFullDto event, long eventId) {
        if (event.getInitiator().getId() == eventId) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }

        if (!State.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        checkEventLimit(event);
    }

    public void eventCheck(Object newEventDto) {
        if (newEventDto == null) {
            throw new DataNotFoundException("Событие не заполнено");
        }
    }

    public void checkEventDate(LocalDateTime eventDate, int plusHours) {
        LocalDateTime timeNow = LocalDateTime.now().plusHours(plusHours);

        if (eventDate.isBefore(timeNow)) {
            throw new ValidationException(String.format(
                    "Дата и время на которые намечено событие не может быть раньше, " +
                            "чем через %d часа от текущего момента", plusHours
            ));
        }
    }

    public void checkEventBeforeUpdate(Event event, long userId) {
        if (event.getInitiator().getId() != userId) {
            throw new ConflictException("Редактировать можно только свои события");
        }

        checkEventStatePublished(event.getState());
    }

    public void checkEventBeforeConfirmRequest(EventFullDto event) {
        if (event.getParticipantLimit() == 0 ||
                !event.isRequestModeration()) {
            throw new ValidationException("Подтверждение заявок не требуется");
        }
        checkEventLimit(event);
    }

    public void checkEventStatePending(Event event) {
        if (!State.PENDING.equals(event.getState())) {
            throw new ConflictException(
                    "Событие должно быть в состоянии ожидания публикации"
            );
        }
    }

    public void checkEventStatePublished(State state) {
        if (State.PUBLISHED.equals(state)) {
            throw new ConflictException(
                    "Изменить можно только отмененные события или события в состоянии ожидания модерации"
            );
        }
    }

    public void checkCompilationDto(NewCompilationDto compilationDto) {
        if (compilationDto == null) {
            throw new DataNotFoundException("Категория не заполнена");
        }
    }

    private void checkEventLimit(EventFullDto event) {
        int limit = event.getParticipantLimit();
        if (limit != 0 && limit <= event.getConfirmedRequests()) {
            throw new ConflictException("Невозможно! Достигнут лимит на данное событие");
        }
    }
}
