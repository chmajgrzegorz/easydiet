package pl.grzegorzchmaj.easydiet.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.Ingredient;
import pl.grzegorzchmaj.easydiet.models.services.HtmlToPdfParser;
import pl.grzegorzchmaj.easydiet.models.services.ShoppingListService;
import pl.grzegorzchmaj.easydiet.models.services.UserInfoService;

import java.util.Map;

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
    public String createShoppingListGet(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }
        model.addAttribute("shoppingList", shoppingListService.createShoppingList());
        return "shoppinglist";
    }

    @PostMapping("/converthtmlshoppinglisttopdfversion")
    public String convertHtmlShoppingListToPdfVersion(Model model, RedirectAttributes attr){
        if(!userInfoService.isLogged()){
            attr.addFlashAttribute("info", "Ta strona jest dostępna tylko dla zalogowanych użytkowników");
            return "redirect:/login";
        }

        String inputFile = "http://localhost:8080/createshoppinglist";
        String outputFile = "C:/Users/chmaj/Desktop/TestPdf.pdf";

        HtmlToPdfParser.generatePDF();

        attr.addFlashAttribute("info", "Lista w formacie pdf została stworzona");
        return "redirect:/home";
    }

    @GetMapping("/test")
    public String test(){

        return "about";
    }

    @PostMapping("/testpost")
    @ResponseBody
    public String testPost(@RequestParam("name") String name, @RequestParam("surname") String surname){
        return "Formularz dziala pomyslnie" + name + "       " + surname ;
    }
}
