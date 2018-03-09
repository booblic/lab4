package lab4.library;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lab4.library.book.Book;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartPageController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/")
    public String startPageMessage(Model model, String logout) {

        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been successfully logged out.");
        }

        User user = userService.getCurrentUser();

        if (user != null) {
            model.addAttribute("logout", "yes");
        }

        model.addAttribute("startMessage", "Welcome to Library!");
        return "startpage";
    }

    @GetMapping(value = "/searchbookoptions")
    public String searchBookOptions() {
        return "searchingbook";
    }

    @PostMapping(value = "/success")
    public String successLogin(Model model) {
        model.addAttribute("message", "You have successfully logged into your account!");
        return "index";
    }
}
