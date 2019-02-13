package pl.grzegorzchmaj.easydiet.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import pl.grzegorzchmaj.easydiet.forms.DietForm;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "user"})
@NoArgsConstructor
@Table(name = "diet")
@ToString(exclude = {"id", "user"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
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
