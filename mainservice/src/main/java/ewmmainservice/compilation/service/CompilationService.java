package ewmmainservice.compilation.service;

import ewmmainservice.compilation.dto.CompilationDto;
import ewmmainservice.compilation.dto.NewCompilationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompilationService {
    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long eventId, long compId);

    void addEventToCompilation(long eventId, long compId);

    void pinCompilationToHomepage(long compId);

    void unpinCompilationToHomepage(long compId);

    List<CompilationDto> findAllCompilations(Boolean pinned, Pageable pageable);

    CompilationDto findCompilationById(long compId);
}
