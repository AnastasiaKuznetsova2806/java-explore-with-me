package ewmmainservice.event.dto;

import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.event.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUpdateEventRequest {
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
