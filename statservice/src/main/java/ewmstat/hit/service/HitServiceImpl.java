package ewmstat.hit.service;

import ewmstat.common.Filter;
import ewmstat.common.validation.CheckDataValidation;
import ewmstat.hit.HitMapper;
import ewmstat.hit.dto.HitDto;
import ewmstat.hit.dto.ViewStatsDto;
import ewmstat.hit.repository.HitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository repository;
    private final CheckDataValidation validation;

    @Override
    public void createHit(HitDto hitDto) {
        validation.checkHit(hitDto);

        repository.save(HitMapper.toHit(hitDto));
    }

    @Override
    public List<ViewStatsDto> findAllStats(Filter filter) {
            if (filter.getUnique()) {
                return repository.findAllViewsDistinctIp(filter.getStart(), filter.getEnd(), filter.getUris());
            } else {
                return repository.findAllViews(filter.getStart(), filter.getEnd(), filter.getUris());
            }
    }
}
