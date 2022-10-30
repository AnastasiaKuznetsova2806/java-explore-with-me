package ewmmainservice.common.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewmmainservice.common.StatusException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private final StackTraceElement[] errors;
    private final String message;
    private final String reason;
    private final StatusException status;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private final LocalDateTime timestamp;
}
