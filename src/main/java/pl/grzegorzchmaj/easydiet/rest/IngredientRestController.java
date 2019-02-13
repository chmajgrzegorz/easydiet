package pl.grzegorzchmaj.easydiet.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorzchmaj.easydiet.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.exceptions.IngredientException;
import pl.grzegorzchmaj.easydiet.repositories.IngredientRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IngredientRestController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientRestController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("/ingredients")
    public ResponseEntity addIngredient(@RequestBody Ingredient ingredient){
        ingredientRepository.save(ingredient);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/ingredients/{ingredientId}")
    public Ingredient getIngredient(@PathVariable Long ingredientId){
        Optional<Ingredient> ingredientOptional =ingredientRepository.findById(ingredientId);
        if(ingredientOptional.isPresent()){
            return ingredientOptional.get();
        }
        throw new IngredientException("Ingredient id not found - " + ingredientId);
    }
}
