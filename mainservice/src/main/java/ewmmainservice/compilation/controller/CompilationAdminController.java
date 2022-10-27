package ewmmainservice.compilation.controller;


import ewmmainservice.compilation.dto.CompilationDto;
import ewmmainservice.compilation.dto.NewCompilationDto;
import ewmmainservice.compilation.service.CompilationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationService service;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.info("Получен запрос на добавление новой подборки '{}' ", compilationDto);
        return service.createCompilation(compilationDto);
    }

    @DeleteMapping(value = "/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        log.info("Получен запрос на удаление подборки: compId={} ", compId);
        service.deleteCompilation(compId);
    }

    @DeleteMapping(value = "/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        log.info("Получен запрос на удаление события eventId={} из подборки compId={} ", eventId, compId);
        service.deleteEventFromCompilation(eventId, compId);
    }

    @PatchMapping(value = "/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        log.info("Получен запрос на добавление события eventId={} в подборку compId={} ", eventId, compId);
        service.addEventToCompilation(eventId, compId);
    }

    @PatchMapping(value = "/{compId}/pin")
    public void pinCompilationToHomepage(@PathVariable long compId) {
        log.info("Получен запрос: закрепить подборку compId={} на главной странице", compId);
        service.pinCompilationToHomepage(compId);
    }

    @DeleteMapping(value = "/{compId}/pin")
    public void unpinCompilationToHomepage(@PathVariable long compId) {
        log.info("Получен запрос: открепить подборку compId={} на главной странице", compId);
        service.unpinCompilationToHomepage(compId);
    }
}
