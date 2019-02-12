package pl.grzegorzchmaj.easydiet.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","ingredientWeights"})
@NoArgsConstructor
@Table(name = "ingredient")
@ToString(exclude = {"id","caloriesPer100g","proteinPer100g","carbohydratePer100g","fatPer100g","ingredientWeights"})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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