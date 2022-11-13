package ewmmainservice.rating.model;

import ewmmainservice.event.model.Event;
import ewmmainservice.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RATINGS")
@IdClass(RatingKey.class)
public class Rating {
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_EVENT")
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;
    private boolean likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return likes == rating.likes && Objects.equals(event, rating.event) && Objects.equals(user, rating.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user, likes);
    }
}
