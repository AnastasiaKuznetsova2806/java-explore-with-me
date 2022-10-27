package ewmmainservice.event.location.repository;

import ewmmainservice.event.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
