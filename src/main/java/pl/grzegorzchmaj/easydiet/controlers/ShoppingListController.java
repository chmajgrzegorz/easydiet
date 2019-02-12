package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.ShoppingList;
import pl.grzegorzchmaj.easydiet.services.ShoppingListService;
import pl.grzegorzchmaj.easydiet.services.UserService;

@Controller
public class ShoppingListController {

    private ShoppingListService shoppingListService;
    private UserService userService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping("/createshoppinglist")
    public String createShoppingListGet(Model model) throws ShoppingServiceException {
        ShoppingList shoppingList = shoppingListService.createShoppingList(userService.getActualUser());
        model.addAttribute("ingredientWeights", shoppingList.getIngredientWeights());
        return "shoppinglist";
    }

}
