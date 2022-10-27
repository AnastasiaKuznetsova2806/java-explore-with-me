package ewmstat.hit;

import ewmstat.common.DataConverter;
import ewmstat.hit.dto.HitDto;
import ewmstat.hit.model.Hit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HitMapper {

    public static Hit toHit(HitDto hitDto) {
        return new Hit(
                hitDto.getId(),
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                DataConverter.toDate(hitDto.getTimestamp())
        );
    }
}
