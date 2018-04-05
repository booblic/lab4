package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.book.FormBook;
import lab4.library.review.Review;
import lab4.library.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookServiceImpl bookServices;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ReviewServiceImp reviewService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "/show")
    public String showBooks(Model model) {

        model.addAttribute("books", bookServices.findAllBook());

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "book/showallbooks";
    }

    @GetMapping(value = "/getaddform")
    public String getAddForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        model.addAttribute("book", new FormBook());

        return "book/formaddbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addbook")
    public String addBook(@ModelAttribute FormBook formBook, Model model) {

        if (bookServices.findByIsbn(formBook.getIsbn()) != null) {

            model.addAttribute("error", "Book with isbn " + formBook.getIsbn() + " already exist");

            model.addAttribute("book", formBook);

            return "book/formaddbook";
        }

        LOG.info("msg: addBook(bookName, isbn, year)");
        bookServices.editBook(formBook);

        return "redirect:/book/show";
    }

    @GetMapping(value = "/getsearchingbybooknameform")
    public String getSearchingForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        return "book/searchingbybooknameform";
    }

    @PostMapping(value = "/searchingbybookname")
    public String searchingByBookName(@RequestParam String bookName, Model model) {

        LOG.info("msg: bookServices.findByBookName({})", bookName);
        List<Book> bookList = bookServices.findByBookName(bookName);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found");
        }
        return "book/showallbooks";
    }

    @GetMapping(value = "/getformedit")
    public String getEditForm(@RequestParam("id") @NotNull Integer bookId, Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        model.addAttribute("book", bookServices.findOne(bookId));

        return "book/formeditbook";
    }

    @GetMapping("/deletebook")
    public String deleteBook(@RequestParam("id") @NotNull Integer bookId, Model model) {

        LOG.info("msg:  bookServices.deleteBook({})", bookId);
        bookServices.deleteBook(bookId);

        return "redirect:/book/show";
    }

    @GetMapping("/getformviewbook")
    public String getFormViewbook(@RequestParam("id") @NotNull Integer bookId, Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: bookServices.findBook({})", bookId);
        Book book = bookServices.findOne(bookId);

        LOG.info("msg: book.getReviews()");
        Set<Review> reviews = book.getReviews();

        model.addAttribute("reviews", reviews);

        model.addAttribute("user", userService.getCurrentUser());

        model.addAttribute("book", book);

        return "book/formviewbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {

        LOG.info("msg: bookServices.editBook({})", formBook.toString());
        Book book = bookServices.editBook(formBook);

        return "redirect:/book/show";
    }

    @PostMapping(value = "/{id}/addreview")
    public String addReview(@PathVariable Integer id, @RequestParam String textReview, @RequestParam String rating) {

        LOG.info("msg: reviewService.addReview({}, {}, {})", id, textReview, rating);
        Review review = reviewService.addReview(id, textReview, rating);

        return "redirect:/book/show";
    }

    @GetMapping(value = "/genreandyearsearchingform")
    public String genreAndYearSearchingForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "book/genreandyearsearchingform";
    }

    @PostMapping(value = "/searchingbygenreandyear")
    public String searchingByGenreAndYear(@RequestParam String genreName, @RequestParam int year, Model model) {

        LOG.info("msg: bookServices.findByYearAndGenreName({}, {})", genreName, year);
        List<Book> bookList = bookServices.findByYearAndGenreName(genreName, year);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with genre " + genreName + " and  year " + year + " a not found");
        }
        return "book/showallbooks";
    }

    @GetMapping(value = "/authorandgenresearchingform")
    public String authorAndGenreSearchingForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "book/authorandgenreform";
    }

    @PostMapping(value = "/searhcingbyauthorandgenre")
    public String searchingByAuthorAndGenre(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String genreName, Model model) {

        LOG.info("msg: bookServices.findByAuthorAndGenreName({}, {}, {})", firstName, lastName, genreName);
        List<Book> bookList = bookServices.findByAuthorAndGenreName(firstName, lastName, genreName);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with author " + firstName + " " + lastName + " and  genre " + genreName + " a not found");
        }

        return "book/showallbooks";
    }

    @PostMapping(value = "/exportbooks")
    public ResponseEntity<StreamingResponseBody> exportBooks(@RequestParam List<Integer> id) {

        ResponseEntity<StreamingResponseBody> responseEntity = null;

        try {
            LOG.info("msg: bookServices.exportBooks({})", id);
            responseEntity = bookServices.exportBooks(id);

        } catch (IOException exception) {

            LOG.error("msg: printStackTrace()", exception);
        }
        return responseEntity;
    }

    @GetMapping(value = "/getsearchbookinternetform")
    public String getFindForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "book/searchbookinternetform";
    }

    @PostMapping(value = "/searchbookinternet")
    public String searchBookInternet(@RequestParam String bookName, Model model) {

        LOG.info("bookServices.searchBookInternet({})", bookName);
        List<PatternBook> books = bookServices.searchBookInternet(bookName);

        if (books.size() != 0) {

            model.addAttribute("books", books);

        } else {

            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found on mybook.ru");
        }

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "book/searchingbookinternetresult";
    }

    @PostMapping(value = "addsearchingbook")
    public String addSearchingBook(@ModelAttribute PatternBook patternBook) {

        LOG.info("bookServices.addSearchingBook({})", patternBook.toString());
        Book book = bookServices.addSearchingBook(patternBook);

        return "redirect:/book/show";
    }
}
