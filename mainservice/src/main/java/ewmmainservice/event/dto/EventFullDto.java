package ewmmainservice.event.dto;

import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.event.location.dto.LocationDto;
import ewmmainservice.event.util.State;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private long id;
    private InitiatorDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private long views;

    @Data
    @AllArgsConstructor
    public static class InitiatorDto {
        private final long id;
        private final String name;
    }
}
