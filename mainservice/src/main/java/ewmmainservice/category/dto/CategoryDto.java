package ewmmainservice.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CategoryDto {
    @NotNull(message = "Поле id не может быть пустым")
    private long id;

    @NotNull(message = "Поле name не заполнено")
    @NotBlank(message = "Поле name не может быть пустым")
    private String name;

    public CategoryDto(long id) {
        this.id = id;
    }
}
