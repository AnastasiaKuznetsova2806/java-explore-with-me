package ewmmainservice.event.util;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DataConverter {
    public LocalDateTime toDate(String date) {
        return LocalDateTime.parse(date, CommonConstant.FORMATTER);
    }

    public String toDateString(LocalDateTime date) {
        return date.format(CommonConstant.FORMATTER);
    }

    public List<State> toState(List<String> states) {
        List<State> stateList;
        try {
            stateList = states.stream()
                    .map(state -> State.valueOf(state.toUpperCase()))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(String.format("Неизвестное значение: %s", states));
        }
        return stateList;
    }
}
