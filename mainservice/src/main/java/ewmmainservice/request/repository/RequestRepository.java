package ewmmainservice.request.repository;

import ewmmainservice.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ewmmainservice.request.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findAllByRequester_IdAndEvent_Id(long userId, long eventId);

    List<Request> findAllByRequester_Id(long userId);

    List<Request> findAllByEvent_Id(long eventId);

    @Query("select count(r.id) " +
            "from Request r " +
            "where r.event.id = ?1 " +
            "and r.status = ?2 ")
    long countConfirmedRequests(long eventId, Status status);

    @Modifying
    @Query("update Request r " +
            "set  r.status = ?1 " +
            "where r.event.id = ?2 " +
            "and r.status = ?3 ")
    void updateRequestsByEventIdAndStatus(Status newStatus, long eventId, Status status);
}
