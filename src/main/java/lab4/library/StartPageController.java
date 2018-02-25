package lab4.library;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartPageController {

    @RequestMapping(value = "/")
    public String startPageMessage(Model model) {
        model.addAttribute("startMessage", "Welcome to Library!");
        return "startpage";
    }
}
