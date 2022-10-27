package ewmmainservice.compilation.controller;

import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import ewmmainservice.compilation.dto.CompilationDto;
import ewmmainservice.compilation.service.CompilationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/compilations")
public class CompilationPublicController {
    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> findAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                    @RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                                    @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение подборок событий с возможностью фильтрации: " +
                "pinned={}, from={}, size={}",pinned, from, size
        );
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findAllCompilations(pinned, pageable);
    }

    @GetMapping(value = "/{compId}")
    public CompilationDto findCompilationById(@PathVariable long compId) {
        log.info("Получен запрос на получение подборки событий по compId={}", compId);
        return service.findCompilationById(compId);
    }
}
