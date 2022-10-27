package ewmmainservice.compilation.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT_COMPILATIONS")
public class EventCompilation implements Serializable {
    @Id
    @Column(name = "ID_EVENT")
    private long eventId;

    @Id
    @Column(name = "ID_COMPILATION")
    private long compilationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCompilation that = (EventCompilation) o;
        return eventId == that.eventId && compilationId == that.compilationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, compilationId);
    }
}
