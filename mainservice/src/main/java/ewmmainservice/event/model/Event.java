package ewmmainservice.event.model;

import ewmmainservice.category.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import ewmmainservice.event.util.State;
import ewmmainservice.event.location.model.Location;
import lombok.*;
import ewmmainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENTS")
public class Event {
    @Id
    @Column(name = "ID_EVENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;

    @Column(nullable = false, length = 7000)
    private String description;

    @Column(name = "EVENT_DATE")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime eventDate;

    @Column(name = "CREATED")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "ID_INITIATOR")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "ID_LOCATION", nullable = false)
    private Location location;

    private boolean paid;

    @Column(nullable = false)
    private int participantLimit;

    @Column(name = "PUBLISHED")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime publishedOn;

    @Column(name = "REQUEST_MODERATION", nullable = false)
    private boolean requestModeration;

    @Enumerated(value = EnumType.ORDINAL)
    private State state;

    @Column(length = 120)
    private String title;

    private long views;
}
