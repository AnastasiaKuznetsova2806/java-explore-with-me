package ewmstat.hit.controller;

import ewmstat.common.DataConverter;
import ewmstat.common.Filter;
import ewmstat.hit.dto.HitDto;
import ewmstat.hit.dto.ViewStatsDto;
import ewmstat.hit.service.HitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@Valid
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService service;

    @PostMapping(value = "/hit")
    public void createHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Получен запрос на сохранение информации {}", hitDto);
        service.createHit(hitDto);
    }

    @GetMapping(value = "/stats")
    public List<ViewStatsDto> findAllStats(@RequestParam @NonNull @NotBlank String start,
                                  @RequestParam @NonNull @NotBlank String end,
                                  @RequestParam List<String> uris,
                                  @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получен запрос на получение статистики по посещениям: " +
                "start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        Filter filter = new Filter(
                DataConverter.toDate(start),
                DataConverter.toDate(end),
                uris,
                unique
        );
        return service.findAllStats(filter);
    }
}
