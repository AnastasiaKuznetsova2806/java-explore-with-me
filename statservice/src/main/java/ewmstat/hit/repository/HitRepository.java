package ewmstat.hit.repository;

import ewmstat.hit.dto.ViewStatsDto;
import ewmstat.hit.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("select new ewmstat.hit.dto.ViewStatsDto(" +
            "h.app, " +
            "h.uri," +
            "count(distinct h.ip)) " +
            "from Hit h " +
            "where h.timestamp between ?1 and ?2 " +
            "and h.uri in (?3) " +
            "group by h.app, h.uri ")
    List<ViewStatsDto> findAllViewsDistinctIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ewmstat.hit.dto.ViewStatsDto(" +
            "h.app, " +
            "h.uri," +
            "count(h.ip)) " +
            "from Hit h " +
            "where h.timestamp between ?1 and ?2 " +
            "and h.uri in (?3) " +
            "group by h.app, h.uri ")
    List<ViewStatsDto> findAllViews(LocalDateTime start, LocalDateTime end, List<String> uris);
}
