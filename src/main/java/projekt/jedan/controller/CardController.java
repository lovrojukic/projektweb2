package projekt.jedan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CardController {

    @Autowired
    private VulnerabillityController vulnerabillityController;


    private Map<Integer, String> cardDatabase = new HashMap<>();

    public CardController() {
        //Ovo je napravljena mala baza podataka za korisnike s PIN, IME, BROJ KARTICE
        cardDatabase.put(6065, "Lovro: 1234-5678-9012-3456");
        cardDatabase.put(6066, "Marko: 2345-6789-0123-4567");
        cardDatabase.put(6067, "Luka: 3456-7890-1234-5678");
    }

    @GetMapping("/card/{userId}")
    public String getCardPage(@PathVariable Integer userId, Model model, HttpServletRequest request) {
        Integer cookieUserId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("userId".equals(cookie.getName())) {
                    cookieUserId = Integer.parseInt(cookie.getValue());
                }
            }
        }


        System.out.println("Access Control Enabled: " + vulnerabillityController.isAccessControlEnabled());
        if (vulnerabillityController.isAccessControlEnabled()) {

            model.addAttribute("cardInfo", cardDatabase.getOrDefault(userId, "Account not found"));
        } else {

            if (cookieUserId != null && cookieUserId.equals(userId)) {
                model.addAttribute("cardInfo", cardDatabase.get(userId));
            } else {
                model.addAttribute("cardInfo", "Access denied");
            }
        }

        model.addAttribute("userId", userId);
        model.addAttribute("accessControlEnabled", vulnerabillityController.isAccessControlEnabled());
        return "cardPage";
    }
}
