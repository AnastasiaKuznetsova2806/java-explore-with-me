package ewmmainservice.event.location.service;

import ewmmainservice.event.location.model.Location;
import ewmmainservice.event.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;

    public Location createLocation(Location location) {
        return repository.save(location);
    }
}
