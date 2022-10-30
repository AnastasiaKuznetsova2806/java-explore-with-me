package ewmmainservice.common;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public final class CommonConstant {
    public static final String DEFAULT_FROM = "0";
    public static final String DEFAULT_SIZE = "10";

    public static final int PLUS_HOURS_PRIVATE = 2;

    public static final int PLUS_HOURS_ADMIN = 1;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String DEFAULT_SORT = "EVENT_DATE";
}
