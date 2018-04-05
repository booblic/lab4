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

@Controller
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookServiceImpl bookServices;

    @RequestMapping(value = "/")
    public String startPageMessage(Model model, String logout) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "startpage";
    }

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
