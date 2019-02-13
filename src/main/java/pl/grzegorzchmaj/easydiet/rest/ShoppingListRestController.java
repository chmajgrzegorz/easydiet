package pl.grzegorzchmaj.easydiet.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.ShoppingList;
import pl.grzegorzchmaj.easydiet.services.ShoppingListService;
import pl.grzegorzchmaj.easydiet.services.UserService;

@RestController()
@RequestMapping("/api")
public class ShoppingListRestController {

    private ShoppingListService shoppingListService;
    private UserService userService;

    @Autowired
    public ShoppingListRestController(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping("/shoppinglist")
    public ShoppingList createShoppingList() throws ShoppingServiceException {
        return shoppingListService.createShoppingList(userService.getActualUser());
    }
}
