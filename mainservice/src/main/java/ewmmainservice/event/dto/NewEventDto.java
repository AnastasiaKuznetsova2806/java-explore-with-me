package ewmmainservice.event.dto;

import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.event.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class NewEventDto {
    @NotNull(message = "Поле annotation не может быть пустым")
    @NotBlank(message = "Поле annotation не может быть пустым")
    @Size(min = 20, max = 2000, message = "Поле annotation не может содержать от 20 до 2000 символов")
    private String annotation;

    @NotNull(message = "Поле category не может быть пустым")
    private CategoryDto category;

    @NotNull(message = "Поле description не может быть пустым")
    @NotBlank(message = "Поле description не может быть пустым")
    @Size(min = 20, max = 7000, message = "Поле description не может содержать от 20 до 7000 символов")
    private String description;

    @NotNull(message = "Поле eventDate не может быть пустым")
    @NotBlank(message = "Поле eventDate не может быть пустым")
    private String eventDate;

    @NotNull(message = "Поле location не может быть пустым")
    private LocationDto location;

    @NotNull(message = "Поле paid не может быть пустым")
    private boolean paid;

    @NotNull(message = "Поле participantLimit не может быть пустым")
    @Positive
    private int participantLimit;

    @NotNull(message = "Поле requestModeration не может быть пустым")
    private boolean requestModeration;

    @NotNull(message = "Поле title не может быть пустым")
    @NotBlank(message = "Поле title не может быть пустым")
    @Size(min = 3, max = 120, message = "Поле title не может содержать от 3 до 120 символов")
    private String title;
}
