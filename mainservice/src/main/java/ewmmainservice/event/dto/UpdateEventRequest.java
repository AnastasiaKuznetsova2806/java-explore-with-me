package ewmmainservice.event.dto;

import ewmmainservice.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UpdateEventRequest {
    @Size(min = 20, max = 2000, message = "Поле annotation не может содержать от 20 до 2000 символов")
    private String annotation;
    private CategoryDto category;

    @Size(min = 20, max = 7000, message = "Поле description не может содержать от 20 до 7000 символов")
    private String description;
    private String eventDate;

    @NotNull(message = "Поле eventId не может быть пустым")
    private long eventId;
    private Boolean paid;
    private Integer participantLimit;

    @Size(min = 3, max = 120, message = "Поле title не может содержать от 3 до 120 символов")
    private String title;
}
