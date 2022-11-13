package ewmmainservice.event.repository;

import ewmmainservice.event.util.State;
import ewmmainservice.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    @Query("select count(r.id) " +
            "from Event e " +
            "join Request r on r.event.id = e.id and r.status = 'APPROVED' " +
            "where e.id = ?1 ")
    long countConfirmedRequests(long eventId);

    Page<Event> findAllByInitiatorId(long userId, Pageable pageable);

    Event findByIdAndInitiatorId(long eventId, long userId);

    Event findByIdAndState(long id, State state);

    @Query("select count(r.event.id) " +
            "from Rating r " +
            "join Event e on e.id = r.event.id " +
            "where r.event.id = ?1 " +
            "and r.likes = ?2 ")
    int countRatingByEventId(long eventId, boolean rating);
}
