package lab4.library.book;

import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/book")
public class BookController {


    @Autowired
    private BookServices bookServices;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping(value = "/show")
    public String showBooks(Model model) {
        model.addAttribute("books", bookServices.findAll());
        return "book/showall";
    }

    @PostMapping(value = "/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("message", bookServices.saveBook(book));
        return "message";
    }

    @GetMapping(value = "/form")
    public String formBook(Model model) {
        model.addAttribute("formBook", new Book());
        return "book/form";
    }
}
