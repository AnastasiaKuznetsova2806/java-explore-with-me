package ewmmainservice.rating.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RatingKey.class)
public class RatingKey implements Serializable {
    private long event;
    private long user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingKey ratingKey = (RatingKey) o;
        return event == ratingKey.event && user == ratingKey.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}
