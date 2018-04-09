package lab4.library.controller;

import lab4.library.service.BookServiceImpl;
import lab4.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Spring MVC controller для стартовой страницы
 * @author Кирилл
 * @version 1.0
 */
@Controller
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserService userService;

    /**
     * Объект сервиса, реализующего бизнес логики для book
     */
    @Autowired
    private BookServiceImpl bookServices;

    /**
     * Метод возвращает имя jsp стартовой страницы
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @RequestMapping(value = "/")
    public String startPageMessage(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "startpage";
    }

    /**
     * Метод возвращает имя jsp для выбора типа поиска книг
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
