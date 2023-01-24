package ewmmainservice.compilation.service;

import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import ewmmainservice.compilation.CompilationMapper;
import ewmmainservice.compilation.dto.CompilationDto;
import ewmmainservice.compilation.dto.NewCompilationDto;
import ewmmainservice.compilation.model.Compilation;
import ewmmainservice.compilation.repository.CompilationRepository;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventService eventService;
    private final CheckDataValidation validation;

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        validation.checkCompilationDto(compilationDto);
        Set<Event> eventSet = new HashSet<>(
                eventService.getAllEventsById(compilationDto.getEvents())
        );
        Compilation compilation = CompilationMapper.toCompilation(compilationDto, eventSet);
        return findCompilationById(repository.save(compilation).getId());
    }

    @Override
    public void deleteCompilation(long compId) {
        getCompilationById(compId);
        repository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(long eventId, long compId) {
        getCompilationById(compId);
        Event event = eventService.getEvent(eventId);
        Compilation compilation = getCompilationById(compId);
        compilation.getEvents().remove(event);
        repository.save(compilation);
    }

    @Override
    public void addEventToCompilation(long eventId, long compId) {
        getCompilationById(compId);
        Event event = eventService.getEvent(eventId);
        Compilation compilation = getCompilationById(compId);
        compilation.getEvents().add(event);
        System.out.println(compilation);
        repository.save(compilation);
    }

    @Override
    public void pinCompilationToHomepage(long compId) {
        Compilation compilation = getCompilationById(compId);
        compilation.setPinned(true);
        repository.save(compilation);
    }

    @Override
    public void unpinCompilationToHomepage(long compId) {
        Compilation compilation = getCompilationById(compId);
        compilation.setPinned(false);
        repository.save(compilation);
    }

    @Override
    public List<CompilationDto> findAllCompilations(Boolean pinned, Pageable pageable) {
        Page<Compilation> compilations;

        if (pinned != null) {
            compilations = repository.findAllByPinned(pinned, pageable);
        } else {
            compilations = repository.findAll(pageable);
        }
        return compilations.stream()
                .map(compilation -> findCompilationById(compilation.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findCompilationById(long compId) {
        return CompilationMapper.toCompilationDto(getCompilationById(compId), eventService);
    }

    private Compilation getCompilationById(long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Событие %d не найдено", compId)));
    }
}
