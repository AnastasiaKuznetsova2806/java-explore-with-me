package ewmmainservice.common.handler;

import ewmmainservice.common.StatusException;
import ewmmainservice.common.exception.ConflictException;
import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleDataNotFoundException(final DataNotFoundException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMassage(),
                "Объект не найден",
                StatusException.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMassage(),
                "Запрос составлен с ошибкой",
                StatusException.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDefaultHandlerExceptionResolver(final MissingServletRequestParameterException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Ошибка параметра запроса",
                StatusException.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidException(final MethodArgumentNotValidException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Не выполнены условия для совершения операции",
                StatusException.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Запрос приводит к нарушению целостности данных",
                StatusException.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMassage(),
                "Запрос приводит к нарушению целостности данных",
                StatusException.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        return new ApiError(
                e.getStackTrace(),
                e.getMessage(),
                "Внутренняя ошибка сервера",
                StatusException.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }
}
