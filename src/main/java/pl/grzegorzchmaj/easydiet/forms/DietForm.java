package pl.grzegorzchmaj.easydiet.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.grzegorzchmaj.easydiet.entities.User;
import java.sql.Date;

@Data
@NoArgsConstructor
public class DietForm {
    private User user;
    private Date startDate;
    private Date endDate;
}
