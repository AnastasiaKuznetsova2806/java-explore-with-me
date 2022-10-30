package ewmmainservice.event.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Filter {
    private List<Long> users;
    private List<State> states;
    private String text;
    private List<Long> categories;
    private boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private boolean onlyAvailable;

    public Filter(List<Long> users,
                  List<State> states,
                  List<Long> categories,
                  LocalDateTime rangeStart,
                  LocalDateTime rangeEnd) {
        this.users = users;
        this.states = states;
        this.categories = categories;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Filter(String text,
                  List<Long> categories,
                  boolean paid,
                  LocalDateTime rangeStart,
                  LocalDateTime rangeEnd,
                  boolean onlyAvailable) {
        this.text = text;
        this.categories = categories;
        this.paid = paid;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.onlyAvailable = onlyAvailable;
    }
}
