package ewmmainservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "Поле email не может быть пустым")
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Электронная почта не соответствует формату электронного адреса")
    private String email;

    @NotNull(message = "Поле name не может быть пустым")
    @NotBlank(message = "Поле name не может быть пустым")
    private String name;
}
