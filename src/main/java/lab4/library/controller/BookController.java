package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.controller.convert.FormBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.review.Review;
import lab4.library.service.BookServices;
import lab4.library.service.GenreService;
import lab4.library.service.ReviewService;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookServices bookServices;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "/show")
    public String showBooks(Model model) {
        logger.info("----------------------------------------------");
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    @GetMapping(value = "/formadd")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/formaddbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
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
        model.addAttribute("book", book);

        if (kind.compareTo("View") == 0) {
            Set<Review> reviews = book.getReviews();
            model.addAttribute("reviews", reviews);
            model.addAttribute("user", userService.getCurrentUser());
            return "book/formviewbook";
        }

        if (kind.compareTo("Delete") == 0) {
            bookServices.deleteBook(id);
            model.addAttribute("books", bookServices.findAllBook());
            return "book/showallbooks";
        }
        return "book/formeditbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {
        Book book = conversionService.convert(formBook, Book.class);

        bookServices.editBook(book);
        return "redirect:/book/show";
    }

    @PostMapping(value = "/{id}/addreview")
    public String addReview(@PathVariable Integer id, @RequestParam String textReview, @RequestParam String rating) {

        Review review = null;

        User user = userService.getCurrentUser();

        Book book = bookServices.findBook(id);

        Map<String, String> bookReview = new HashMap<>();
        bookReview.put(textReview, rating);

        if (reviewService.findByBookAndUser(book, user) != null) {
            review = reviewService.findByBookAndUser(book, user);
            review.setBookReview(bookReview);
            reviewService.saveReview(review);
        } else {
            review = new Review();
            review.setUser(user);
            review.setBook(book);
            review.setBookReview(bookReview);
        }
        reviewService.saveReview(review);

        return "redirect:/book/show";
    }

    @GetMapping(value = "/genreandyearsearchingform")
    public String genreAndYearSearchingForm(Model model) {
        model.addAttribute("value", "test js");
        return "book/genreandyearsearchingform";
    }

    @PostMapping(value = "/searcingbygenreandyear")
    public String searcingByGenreAndYear(@RequestParam String genreName, @RequestParam int year, Model model) {
        Book book = new Book();
        book.setYear(2013);
        Example<Book> example = Example.of(book);
        //book.setYear(year);
        /*Set<Genre> genreSet = new HashSet<>();
        genreSet.add(genreService.findByGenreName(genreName));
        book.setGenres(genreSet);*/
        //model.addAttribute("books", bookServices.findByYearAndGenreName(genreName, year));
        model.addAttribute("books", bookServices.findBook(example));
        System.out.println("-----------------------------------------------------------");
        System.out.println(bookServices.findBook(example));
        System.out.println("-----------------------------------------------------------");
        return "book/showallbooks";
    }

    @GetMapping(value = "/authorandgenresearchingform")
    public String authorAndGenreSearchingForm(Model model) {
        model.addAttribute("value", "test js");
        return "book/authorandgenreform";
    }

    @PostMapping(value = "/searcingbyauthorandgenre")
    public String searcingByAuthorAndGenre(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String genreName, Model model) {
        model.addAttribute("books", bookServices.findByAuthorAndGenreName(firstName, lastName, genreName));
        return "book/showallbooks";
    }

    @GetMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("value", "test js");
        return "book/testjsp";
    }

    @PostMapping(value = "/params/arrays")
    public String paramsAsArrays(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String genreName, Model model) {
        model.addAttribute("books", bookServices.findByAuthorAndGenreName(firstName, lastName, genreName));
        return "book/showallbooks";
    }
}
