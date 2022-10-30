package ewmstat.hit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class HitDto {
    private long id;

    @NotNull(message = "Поле app не может быть пустым")
    @NotBlank(message = "Поле app не может быть пустым")
    private String app;

    @NotNull(message = "Поле uri не может быть пустым")
    @NotBlank(message = "Поле uri не может быть пустым")
    private String uri;

    @NotNull(message = "Поле ip не может быть пустым")
    @NotBlank(message = "Поле ip не может быть пустым")
    private String ip;

    @NotNull(message = "Поле timestamp не может быть пустым")
    @NotBlank(message = "Поле timestamp не может быть пустым")
    private String timestamp;
}
