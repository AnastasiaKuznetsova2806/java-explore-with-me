package ewmmainservice.category.service;

import ewmmainservice.category.dto.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);

    List<CategoryDto> findAllCategory(Pageable pageable);

    CategoryDto findCategoryById(long catId);
}
