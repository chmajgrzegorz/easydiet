package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.*;
import pl.grzegorzchmaj.easydiet.models.forms.DietForm;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","user"})
@NoArgsConstructor
@Table(name = "diet")
public class Diet {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "diet_meal_info", joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_info_id"))
    private List<MealInfo> meals = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public Diet(DietForm dietForm){
        this.user = dietForm.getUser();
        this.startDate = dietForm.getStartDate().toLocalDate();
        this.endDate = dietForm.getEndDate().toLocalDate();
    }

}
