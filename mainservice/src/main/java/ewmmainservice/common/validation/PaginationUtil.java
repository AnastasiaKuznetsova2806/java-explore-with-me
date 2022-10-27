package ewmmainservice.common.validation;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@UtilityClass
public class PaginationUtil {
    public static Pageable getPageable(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }

    public static Pageable getPageable(int from, int size, String sort) {
        int page = from / size;

        if (sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        }
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
    }
}
