package ewmmainservice.category.controller;

import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.category.service.CategoryService;
import ewmmainservice.common.CommonConstant;
import ewmmainservice.common.validation.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping(value = "/admin/categories")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto newCategoryDto) {
        log.info("Получен запрос на добавление категории: '{}' ", newCategoryDto);
        return service.createCategory(newCategoryDto);
    }

    @PatchMapping(value = "/admin/categories")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос на обновление категории: '{}' ", categoryDto);
        return service.updateCategory(categoryDto);
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> findAllCategory(@RequestParam(defaultValue = CommonConstant.DEFAULT_FROM) int from,
                                             @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {
        log.info("Получен запрос на получение информации о категориях: from={}, size={}", from, size);
        Pageable pageable = PaginationUtil.getPageable(from, size);
        return service.findAllCategory(pageable);
    }

    @GetMapping(value = "/categories/{catId}")
    public CategoryDto findCategoryById(@PathVariable long catId) {
        log.info("Получен запрос на получение информации о категории: catId={}", catId);
        return service.findCategoryById(catId);
    }

    @DeleteMapping(value = "/admin/categories/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        log.info("Получен запрос на удаление категории: catId={} ", catId);
        service.deleteCategory(catId);
    }
}
