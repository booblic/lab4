package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.controller.convert.FormBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.review.Review;
import lab4.library.service.BookServices;
import lab4.library.service.ReviewService;
import lab4.library.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ConversionService conversionService;

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

/*    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addbook")
    public String addBook(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year) {
        bookServices.saveBook(bookName, isbn, year);
        return "redirect:/book/show";
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addbook")
    public String addBook(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year) {

        Set<Book> bookSet = new HashSet<>();

        for (int i = 0; i < bookName.length; i++) {
            Book book = new Book();
            book.setBookName(bookName[i]);
            book.setIsbn(isbn[i]);
            book.setYear(year[i]);
            bookSet.add(book);
        }

        bookServices.saveBook(bookSet);
        return "redirect:/book/show";
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
        List<Book> bookList = bookServices.findByBookName(bookName);
        if (bookList.size() != 0) {
            model.addAttribute("books", bookList);
        } else {
            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found.");
        }
        return "book/showallbooks";
    }

    @PostMapping(value = "/{id}/formedit")
    public String editForm(@PathVariable Integer id, @RequestParam String kind, Model model) {
        Book book = bookServices.findBook(id);
        Set<Genre> genreSet = book.getGenres();
        Set<Author> authorSet = book.getAuthors();
        Set<Publisher> publisherSet = book.getPublishers();
        model.addAttribute("book", book);
        model.addAttribute("genres", genreSet);
        model.addAttribute("authors", authorSet);
        model.addAttribute("publishers", publisherSet);

        if (kind.compareTo("View") == 0) {
            Set<Review> reviews = book.getReviews();
            model.addAttribute("reviews", reviews);
            model.addAttribute("user", userService.getCurrentUser());
            return "book/formviewbook";
        }
        return "book/formeditbook";
    }

/*    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/editbook")
    public String editBook(@PathVariable Integer id,
                           @ModelAttribute Book book,
                           @RequestParam String[] genreName,
                           @RequestParam String[] firstName,
                           @RequestParam String[] lastName,
                           @RequestParam String[] middleName,
                           @RequestParam String[] publisherName, FormBook formBook) {
        book.setBookId(id);

        bookServices.editBook(book, genreName, firstName, lastName, middleName, publisherName);
        return "redirect:/book/show";
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {
        Book book = conversionService.convert(formBook, Book.class);

        bookServices.editBook(book);
        return "redirect:/book/show";
    }

    @PostMapping(value = "/{id}/addreview")
    public String addreview(@PathVariable Integer id, @RequestParam String[] textReview, @RequestParam Integer rating) {
        Book book = bookServices.findBook(id);
        reviewService.saveReview(textReview[0], rating, book, userService.getCurrentUser());
        return "redirect:/book/show";

    }

    @GetMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("value", "test js");
        return "book/testjsp";
    }

    @PostMapping(value = "/params/arrays")
    public String paramsAsArrays(Model model) {
        return "message";
    }
}
