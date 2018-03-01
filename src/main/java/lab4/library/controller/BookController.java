package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.services.AuthorServices;
import lab4.library.services.BookServices;
import lab4.library.services.GenreServices;
import lab4.library.services.PublisherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

    @GetMapping(value = "/addform")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/bookaddform";
    }

    @PostMapping(value = "/addbook")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("message", bookServices.saveBook(book).getBookName() + " is added");
        return "message";
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

    @PostMapping(value = "/{id}/editform")
    public String editForm(@PathVariable Integer id, Model model) {
        Book book = bookServices.findBook(id);
        model.addAttribute("book", book);
        return "book/bookeditform";
    }

    @PostMapping(value = "/{id}/editbook")
    public String editBook(@PathVariable Integer id,
                           @ModelAttribute Book book,
                           @ModelAttribute(name = "genreNames") String genreNames,
                           @ModelAttribute(name = "authorNames") String authorNames,
                           @ModelAttribute(name = "publisherNames") String publisherNames,
                           Model model) {
        book.setBookId(id);
        bookServices.editBook(book, genreNames, authorNames, publisherNames);
        model.addAttribute("books", bookServices.saveBook(book));
        return "/book/show";
    }
}
