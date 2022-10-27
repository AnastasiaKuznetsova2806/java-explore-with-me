package ewmmainservice.compilation;

import ewmmainservice.compilation.dto.CompilationDto;
import ewmmainservice.compilation.dto.NewCompilationDto;
import ewmmainservice.compilation.model.Compilation;
import ewmmainservice.event.EventMapper;
import ewmmainservice.event.model.Event;
import ewmmainservice.event.service.EventService;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, Set<Event> eventSet) {
        return new Compilation(
                0,
                eventSet,
                newCompilationDto.isPinned(),
                newCompilationDto.getTitle()
        );
    }

    public static CompilationDto toCompilationDto(Compilation compilation, EventService eventService) {
        return new CompilationDto(
                compilation.getEvents().stream()
                        .map(
                                event -> EventMapper.toEventShortDto(eventService.findEventById(
                                        event.getId(),
                                        false
                                ))
                        )
                        .collect(Collectors.toList()),
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }
}
