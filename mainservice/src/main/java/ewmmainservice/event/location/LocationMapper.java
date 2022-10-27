package ewmmainservice.event.location;

import ewmmainservice.event.location.dto.LocationDto;
import ewmmainservice.event.location.model.Location;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        return new Location(
                0L,
                locationDto.getLat(),
                locationDto.getLon()
        );
    }

    public static LocationDto toLocationDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }
}
