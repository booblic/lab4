package lab4.library.controller;

import lab4.library.service.BookServiceImpl;
import lab4.library.service.UserService;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Spring MVC controller for the start page
 * @author Кирилл
 * @version 1.0
 */
@Controller
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserService userService;

    /**
     * A service object that implements business logic for a book
     */
    @Autowired
    private BookServiceImpl bookServices;

    /**
     * The method returns the name of the jsp start page
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @RequestMapping(value = "/")
    public String startPageMessage(Model model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {

            model.addAttribute("username", currentUser.getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }

            if (currentUser.getSubscription() != null) {

                model.addAttribute("subscription", currentUser.getSubscription());
            }
        }
        return "startpage";
    }

    /**
     * The method returns the name jsp to select the type of book search
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searchbookoptions")
    public String searchBookOptions(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "searchingbook";
    }

    @PostMapping(value = "/success")
    public String successLogin(Model model) {

        model.addAttribute("message", "You have successfully logged into your account!");

        return "";
    }
}
