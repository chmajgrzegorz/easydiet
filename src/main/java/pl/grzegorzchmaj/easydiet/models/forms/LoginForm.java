package pl.grzegorzchmaj.easydiet.models.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {

    private String login;
    private String password;
}
