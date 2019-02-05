package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.exceptions.ShoppingServiceException;
import pl.grzegorzchmaj.easydiet.models.ShoppingList;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.models.services.ShoppingListService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

@Controller
public class ShoppingListController {

    private ShoppingListService shoppingListService;
    private UserInfoService userInfoService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, UserInfoService userInfoService) {
        this.shoppingListService = shoppingListService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/createshoppinglist")
    public String createShoppingListGet(Model model, RedirectAttributes attr) throws ShoppingServiceException {
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        ShoppingList shoppingList = shoppingListService.createShoppingList(userInfoService.getUser());
        model.addAttribute("ingredientWeights", shoppingList.getIngredientWeights());
        return "shoppinglist";
    }

}
