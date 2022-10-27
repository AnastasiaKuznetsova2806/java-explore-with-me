package ewmmainservice.event.dto;

import ewmmainservice.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String eventDate;
    private long id;
    private InitiatorDto initiator;
    private boolean paid;
    private String title;
    private long views;

    @Data
    @AllArgsConstructor
    public static class InitiatorDto {
        private final long id;
        private final String name;
    }
}
