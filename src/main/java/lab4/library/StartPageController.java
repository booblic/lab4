package lab4.library;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.service.BookServices;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class StartPageController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookServices bookServices;

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

    @GetMapping(value = "/searchingform")
    public String getSearchingForm() {
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
        List<Book> books = bookServices.searching(bookName, isbn, year, genreName, firstName, lastName, middleName, publisherName);
        model.addAttribute("books", books);
        return "book/showallbooks";
    }
}
