package ewmmainservice.category.service;

import ewmmainservice.category.CategoryMapper;
import ewmmainservice.category.dto.CategoryDto;
import ewmmainservice.category.model.Category;
import ewmmainservice.category.repository.CategoryRepository;
import ewmmainservice.common.exception.DataNotFoundException;
import ewmmainservice.common.validation.CheckDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CheckDataValidation validation;

    @Override
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        validation.categoryCheck(newCategoryDto);
        Category category = CategoryMapper.toCategory(newCategoryDto);
        return findCategoryById(repository.save(category).getId());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        validation.categoryCheck(categoryDto);
        Category category = CategoryMapper.toCategory(categoryDto);
        return findCategoryById(repository.save(category).getId());
    }

    @Override
    public void deleteCategory(long catId) {
        findCategoryById(catId);
        repository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> findAllCategory(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(long catId) {
        if (repository.findById(catId).isEmpty()) {
            throw new DataNotFoundException(String.format("Категория %d не найдена", catId));
        }
        return CategoryMapper.toCategoryDto(repository.findById(catId).get());
    }
}
