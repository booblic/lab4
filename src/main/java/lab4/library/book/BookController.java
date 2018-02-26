package lab4.library.book;

import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    @GetMapping(value = "/addform")
    public String addForm() {
        return "book/bookaddform";
    }

    @PostMapping(value = "/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("message", bookServices.saveBook(book));
        return "message";
    }

    @GetMapping(value = "/searchform")
    public String searchForm() {
        return "book/booksearchform";
    }

    @PostMapping(value = "/search")
    public String searchBook(@ModelAttribute(name = "bookName") String bookName, Model model) {
        model.addAttribute("books", bookServices.findByBookName(bookName));
        return "book/showallbooks";
    }

    @PostMapping(value = "/changebook")
    public String changeBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("books", bookServices.saveBook(book));
        return "/book/show";
    }
}
