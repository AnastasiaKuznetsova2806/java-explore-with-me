package ewmstat.common.validation;

import ewmstat.common.exception.DataNotFoundException;
import ewmstat.hit.dto.HitDto;
import org.springframework.stereotype.Component;

@Component
public class CheckDataValidation {

    public void checkHit(HitDto hitDto) {
        if (hitDto == null) {
            throw new DataNotFoundException("Информация не заполнена");
        }
    }
}
