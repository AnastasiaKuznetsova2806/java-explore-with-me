package ewmmainservice.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    @NotNull(message = "Поле events не может быть пустым")
    private List<Long> events;

    @NotNull(message = "Поле pinned не может быть пустым")
    private boolean pinned;

    @NotNull(message = "Поле title не может быть пустым")
    @NotBlank(message = "Поле title не может быть пустым")
    @Size(min = 3, max = 120, message = "Поле title не может содержать от 3 до 120 символов")
    private String title;
}
