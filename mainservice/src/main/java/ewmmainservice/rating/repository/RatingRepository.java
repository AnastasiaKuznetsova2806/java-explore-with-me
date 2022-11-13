package ewmmainservice.rating.repository;

import ewmmainservice.rating.model.Rating;
import ewmmainservice.rating.model.RatingKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    @Query("select distinct r.event.id " +
            "from Rating r " +
            "where r.likes = ?1 ")
    List<Long> findAllRating(boolean like, Pageable pageable);
}
