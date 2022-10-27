package ewmstat.hit.service;

import ewmstat.common.Filter;
import ewmstat.hit.dto.HitDto;
import ewmstat.hit.dto.ViewStatsDto;

import java.util.List;

public interface HitService {
    void createHit(HitDto hitDto);

    List<ViewStatsDto> findAllStats(Filter filter);
}
