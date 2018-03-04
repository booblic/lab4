package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.service.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping(value = "/formadd")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/formaddbook";
    }

    @PostMapping(value = "/addbook")
    public String addBook(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year, Model model) {
        bookServices.saveBook(bookName, isbn, year);
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    /*@PostMapping(value = "/add")
    public String addBook(@ModelAttribute Book book, @ModelAttribute Author author, @ModelAttribute Publisher publisher, Model model) {

        Author a = authorServices.saveAuthor(author);
        Set<Author> authorSet = new HashSet<>();
        authorSet.add(a);

        Publisher p = publisherServices.savePublisher(publisher);
        Set<Publisher> publisherSet = new HashSet<>();
        publisherSet.add(p);

        book.setAuthors(authorSet);
        book.setPublishers(publisherSet);

        model.addAttribute("message", bookServices.saveBook(book).getBookName() + " is add");
        return "message";
    }*/

    /*<br>
    <h2>Author info</h2>
    FirstName <input type="text" name="firstName"/>
    LastName <input type="text" name="lastName"/>
    MiddleName <input type="text" name="middleName"/>
        <br>
    <h2>Publisher info</h2>
    PublisherName <input type="text" name="publisherName"/>*/

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/searchingform";
    }

    @PostMapping(value = "/search")
    public String searchBookByBookName(@RequestParam String bookName, Model model) {
        model.addAttribute("books", bookServices.findByBookName(bookName));
        return "book/showallbooks";
    }

    @PostMapping(value = "/{id}/formedit")
    public String editForm(@PathVariable Integer id, Model model) {
        Book book = bookServices.findBook(id);
        Set<Genre> genreSet = book.getGenres();
        Set<Author> authorSet = book.getAuthors();
        Set<Publisher> publisherSet = book.getPublishers();
        model.addAttribute("book", book);
        model.addAttribute("genres", genreSet);
        model.addAttribute("authors", authorSet);
        model.addAttribute("publishers", publisherSet);
        return "book/formeditbook";
    }

    @PostMapping(value = "/{id}/editbook")
    public String editBook(@PathVariable Integer id,
                           @ModelAttribute Book book,
                           @RequestParam String[] genreName,
                           @RequestParam String[] firstName,
                           @RequestParam String[] lastName,
                           @RequestParam String[] middleName,
                           @RequestParam String[] publisherName,
                           Model model) {
        book.setBookId(id);
        System.out.println("---------------------------------------------------------");
        for (String s: genreName) {
            System.out.println("--"+s+"--");
            if (s.compareTo("") == 0) {
                System.out.println("++++++++++++++++++");
            }
        }
        System.out.println("---------------------------------------------------------");
        bookServices.editBook(book, genreName, firstName, lastName, middleName, publisherName);
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    @GetMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("value", "test js");
        return "/book/testjsp";
    }

    @PostMapping(value = "/params/arrays")
    public String paramsAsArrays(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year, Model model) {
        model.addAttribute("message", bookName.length);
        return "message";
    }
}
