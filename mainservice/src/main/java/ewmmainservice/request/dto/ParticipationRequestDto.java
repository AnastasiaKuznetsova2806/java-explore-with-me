package ewmmainservice.request.dto;

import ewmmainservice.request.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    @NotNull(message = "Поле created не может быть пустым")
    private LocalDateTime created;

    @NotNull(message = "Поле event не может быть пустым")
    private long event;
    private long id;

    @NotNull(message = "Поле requester не может быть пустым")
    private Long requester;

    @NotNull(message = "Поле status не может быть пустым")
    @NotBlank(message = "Поле status не может быть пустым")
    private Status status;
}
