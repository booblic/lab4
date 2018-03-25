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

        if (logout != null) {
            LOG.info("msg: model.addAttribute(\"logoutMessage\", \"You have been successfully logged out.\");");
            model.addAttribute("logoutMessage", "You have been successfully logged out.");
        }
        LOG.info("msg: User user = userService.getCurrentUser();");
        User user = userService.getCurrentUser();

        if (user != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("logout", "yes");
        }

        if (userService.hasRole("ROLE_ADMIN")) {
            LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
            model.addAttribute("admin", "yes");
        }
        LOG.info("msg:  model.addAttribute(\"startMessage\", \"Welcome to Library!\");");
        model.addAttribute("startMessage", "Welcome to Library!");
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

    @GetMapping(value = "/searchingform")
    public String getSearchingForm() {
        LOG.info("msg: return \"searching\";");
        return "searching";
    }

    @PostMapping(value = "/searching")
    public String searching(@RequestParam String bookName,
                            @RequestParam String isbn,
                            @RequestParam Integer year,
                            @RequestParam String genreName,
                            @RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String middleName,
                            @RequestParam String publisherName, Model model) {
        LOG.info("msg: List<Book> books = bookServices.searching(bookName, isbn, year, genreName, firstName, lastName, middleName, publisherName); " + bookName + isbn + year + genreName + firstName + lastName + middleName + publisherName);
        List<Book> books = bookServices.searching(bookName, isbn, year, genreName, firstName, lastName, middleName, publisherName);
        LOG.info("msg: model.addAttribute(\"books\", books);");
        model.addAttribute("books", books);
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }
}
