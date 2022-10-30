package ewmmainservice.compilation.model;

import ewmmainservice.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMPILATIONS")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "EVENT_COMPILATIONS",
            joinColumns = {@JoinColumn(name = "ID_EVENT")},
            inverseJoinColumns = {@JoinColumn(name = "ID_COMPILATION")}
    )
    @ToString.Exclude
    private Set<Event> events = new HashSet<>();

    @Column(nullable = false)
    private boolean pinned;

    @Column(nullable = false, length = 120)
    private String title;
}
