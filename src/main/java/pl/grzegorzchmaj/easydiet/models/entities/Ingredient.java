package pl.grzegorzchmaj.easydiet.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "calories")
    private Float caloriesPer100g;
    @Column(name = "proteins")
    private Float proteinPer100g;
    @Column(name = "carbohydrates")
    private Float carbohydratePer100g;
    @Column(name = "fat")
    private Float fatPer100g;
    @OneToMany(mappedBy = "ingredient", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
    fetch = FetchType.EAGER)
    private List<IngredientWeight> ingredientWeights;

}
