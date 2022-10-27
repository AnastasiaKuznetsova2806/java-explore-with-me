package ewmstat.common;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DataConverter {
    public LocalDateTime toDate(String date) {
        return LocalDateTime.parse(date, CommonConstant.FORMATTER);
    }
}
