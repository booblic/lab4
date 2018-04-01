package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.service.BookServices;
import lab4.library.service.UserService;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookServices bookServices;

    @RequestMapping(value = "/")
    public String startPageMessage(Model model, String logout) {

        if (userService.getCurrentUser() != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {
                LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: return \"startpage\";");
        return "startpage";
    }

    @GetMapping(value = "/searchbookoptions")
    public String searchBookOptions() {
        LOG.info("msg: return \"searchingbook\";");
        return "searchingbook";
    }

    @PostMapping(value = "/success")
    public String successLogin(Model model) {
        LOG.info("msg: model.addAttribute(\"message\", \"You have successfully logged into your account!\");");
        model.addAttribute("message", "You have successfully logged into your account!");
        return "";
    }
}
