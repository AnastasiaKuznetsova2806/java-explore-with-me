package ewmmainservice.event.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LocationDto {
    @NotNull(message = "Поле lat не может быть пустым")
    @Range(min = -90, max = 90)
    private float lat;

    @NotNull(message = "Поле lon не может быть пустым")
    @Range(min = -180, max = 180)
    private float lon;
}
