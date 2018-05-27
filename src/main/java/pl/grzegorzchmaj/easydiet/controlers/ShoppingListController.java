package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.services.ShoppingListService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;

import java.util.Map;

@Controller
public class ShoppingListController {

    ShoppingListService shoppingListService;
    UserInfoService userInfoService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, UserInfoService userInfoService) {
        this.shoppingListService = shoppingListService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/createshoppinglist")
    @ResponseBody
    public String createShoppingListGet(RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        return shoppingListService.createShoppingList()+"";
    }
}
