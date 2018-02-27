package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.FormatFlagsConversionMismatchException;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping(value = "/show")
    public String showBooks(Model model) {
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    @GetMapping(value = "/addform")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/bookaddform";
    }

    @PostMapping(value = "/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("message", bookServices.saveBook(book).getBookName() + " is add");
        return "message";
    }

    @GetMapping(value = "/searchform")
    public String searchForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/booksearchform";
    }

    @PostMapping(value = "/search")
    public String searchBook(@ModelAttribute(name = "bookName") String bookName, Model model) {
        model.addAttribute("books", bookServices.findByBookName(bookName));
        return "book/showallbooks";
    }

    @PostMapping(value = "/{id}/changeform")
    public String changeForm(@PathVariable Integer id, Model model) {
        Book book = bookServices.findBook(id);
        model.addAttribute("book", book);
        return "book/bookchangeform";
    }

    @PostMapping(value = "/{id}/changebook")
    public String changeBook(@PathVariable Integer id, @ModelAttribute Book book, Model model) {
        book.setBookId(id);
        model.addAttribute("books", bookServices.saveBook(book));
        return "/book/show";
    }
}
