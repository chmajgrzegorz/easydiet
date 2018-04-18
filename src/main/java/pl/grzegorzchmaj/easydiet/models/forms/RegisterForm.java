package pl.grzegorzchmaj.easydiet.models.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.enums.PhysicalActivity;
import pl.grzegorzchmaj.easydiet.enums.Plans;
import pl.grzegorzchmaj.easydiet.enums.Sex;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegisterForm {

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, message = "Login musi mieć conajmniej 3 znaki")
    @Size(max = 20, message = "Login może mieć maksymalnie 20 znaków")
    private String login;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 8, message = "Hasło musi mieć conajmniej 8 znaków")
    private String password;
    @NotNull(message = "Pole nie może być puste")
    @Min(value = 40, message = "Waga musi być większa niż 30")
    private Float weight;
    @NotNull(message = "Pole nie może być puste")
    @Min(value = 130, message = "Wzrost musi być większy niż 130")
    private Float height;
    private Sex sex;
    @NotNull(message = "Pole nie może być puste")
    @Min(value = 15, message = "Wiek musi być większy niż 15 lat")
    private Integer age;
    private PhysicalActivity physicalActivity;
    private Plans plans;

}
