package ewmmainservice.request.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewmmainservice.event.model.Event;
import lombok.*;
import ewmmainservice.request.Status;
import ewmmainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REQUESTS")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "ID_EVENT")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ID_REQUESTER")
    private User requester;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
