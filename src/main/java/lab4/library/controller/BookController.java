package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.book.FormBook;
import lab4.library.review.Review;
import lab4.library.service.*;
import lab4.library.user.Role;
import lab4.library.user.User;
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
import java.time.LocalDate;
import java.util.*;

/**
 * Spring MVC controller for entity  book
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    /**
     * A service object that implements business logic for a book
     */
    @Autowired
    private BookServiceImpl bookServices;

    /**
     * Object of service that implements business logic for author
     */
    @Autowired
    private AuthorServiceImpl authorService;

    /**
     * Object of service that implements business logic for review
     */
    @Autowired
    private ReviewServiceImp reviewService;

    /**
     * The service object that implements the business logic for the publisher
     */
    @Autowired
    private PublisherServiceImpl publisherService;

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * The object of the service that implements the business logic for the genre
     */
    @Autowired
    private GenreServiceImpl genreService;

    /**
     * The object of the service for converting
     */
    @Autowired
    private ConversionService conversionService;

    /**
     * The method gets all the books, adds them to the holder for model attributes, and returns the name jsp to display books
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method returns the name jsp to add a book
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method gets an object containing information about the book, renders it to the service level and returns the name jsp to display the books
     * @param model - defines a holder for model attributes
     * @param formBook - book information object
     * @return name jsp
     */
    @PostMapping(value = "/addbook")
    public String addBook(@ModelAttribute FormBook formBook, Model model) {

        if (bookServices.findByIsbn(formBook.getIsbn()) != null) {

            model.addAttribute("error", "Book with isbn " + formBook.getIsbn() + " already exist");

            model.addAttribute("book", formBook);

            return "book/formaddbook";
        }

        LOG.info("msg: addBook(bookName, isbn, year)");
        bookServices.addOrEditBook(formBook);

        return "redirect:/book/show";
    }

    /**
     * The method returns the name jsp to find the book by name
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method gets books with given names, adds them to the holder for model attributes, and returns the name jsp to display books
     * @param model - defines a holder for model attributes
     * @param bookName - name of the book
     * @return name jsp
     */
    @GetMapping(value = "/searchingbybookname")
    public String searchingByBookName(@RequestParam(value = "bookName") String bookName, Model model) {

        LOG.info("msg: bookServices.findByBookName({})", bookName);
        List<Book> bookList = bookServices.findByBookName(bookName);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found");
        }
        return "book/showallbooks";
    }

    /**
     * The method returns the name jsp for editing the book
     * @param bookId - id book
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getformedit")
    public String getEditForm(@RequestParam("id") @NotNull Integer bookId, Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        model.addAttribute("book", bookServices.findOne(bookId));
        model.addAttribute("summary", bookServices.getBookSummary(bookId));
        return "book/formeditbook";
    }

    /**
     * The method accesses the server to remove the book and returns the name jsp to display books
     * @param bookId - id book
     * @return name jsp
     */
    @GetMapping("/deletebook")
    public String deleteBook(@RequestParam("id") @NotNull Integer bookId) {

        LOG.info("msg:  bookServices.deleteBook({})", bookId);
        bookServices.deleteBook(bookId);

        return "redirect:/book/show";
    }

    /**
     * The method fills the holder for model attributes and returns the name jsp to view information about the book
     * @param bookId - id book
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping("/getformviewbook")
    public String getFormViewBook(@RequestParam("id") @NotNull Integer bookId, Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: bookServices.findBook({})", bookId);
        Book book = bookServices.findOne(bookId);

        LOG.info("msg: book.getReviews()");
        List<Review> reviews = book.getReviews();

        model.addAttribute("reviews", reviews);

        model.addAttribute("user", userService.getCurrentUser());

        model.addAttribute("book", book);

        return "book/formviewbook";
    }

    /**
     * The method accesses the service to edit the resulting book and returns the name jsp to display books
     * @param formBook - book information object
     * @return name jsp
     */
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {

        LOG.info("msg: bookServices.editBook({})", formBook.toString());
        Book book = (Book) bookServices.addOrEditBook(formBook);

        return "redirect:/book/show";
    }

    /**
     * The method accesses the service to create or edit a review and returns the name of jsp to display books
     * @param id - id book
     * @param textReview - review text
     * @param rating - rating
     * @return name jsp
     */
    @PostMapping(value = "/{id}/addreview")
    public String addReview(@PathVariable Integer id, @RequestParam String textReview, @RequestParam Integer rating) {

        LOG.info("msg: reviewService.addReview({}, {}, {})", id, textReview, rating);
        Review review = reviewService.addOrEditReview(id, textReview, rating);

        return "redirect:/book/show";
    }

    /**
     * The method returns the name jsp to find the book by year and genre
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method gets books with a given year and genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     * @param genreName - genre name
     * @param year - name of the book
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searchingbygenreandyear")
    public String searchingByGenreAndYear(@RequestParam(value = "genreName") String genreName, @RequestParam(value = "year") Integer year, Model model) {

        LOG.info("msg: bookServices.findByYearAndGenreName({}, {})", genreName, year);
        List<Book> bookList = bookServices.findByYearAndGenreName(genreName, year);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with genre " + genreName + " and  year " + year + " a not found");
        }
        return "book/showallbooks";
    }

    /**
     * The method returns the name jsp to search for books by author and genre
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method gets books with a given author and genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     * @param firstName - author's name
     * @param lastName - surname of the author
     * @param genreName - genre name
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searhcingbyauthorandgenre")
    public String searchingByAuthorAndGenre(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName, @RequestParam(value = "genreName") String genreName, Model model) {

        LOG.info("msg: bookServices.findByAuthorAndGenreName({}, {}, {})", firstName, lastName, genreName);
        List<Book> bookList = bookServices.findByAuthorAndGenreName(firstName, lastName, genreName);

        if (bookList.size() != 0) {

            model.addAttribute("books", bookList);

        } else {

            model.addAttribute("error", "Sorry, books with author " + firstName + " " + lastName + " and  genre " + genreName + " a not found");
        }

        return "book/showallbooks";
    }

    /**
     * The method accesses the service to obtain a file with information about the books
     * @param id - list of id books
     * @return file with information about books
     */
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

    /**
     * The method returns the name jsp to find the book on the Internet
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method calls the service to retrieve a list of books from an external source and returns the name jsp to display books
     * @param model - defines a holder for model attributes
     * @param bookName - name of the book
     * @return name jsp
     */
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

    /**
     * The method accesses the service to add a book and returns the name jsp to display books
     * @param patternBook - object containing information about book
     * @return name jsp
     */
    @PostMapping(value = "/addsearchingbook")
    public String addSearchingBook(@ModelAttribute PatternBook patternBook) {

        LOG.info("bookServices.addSearchingBook({})", patternBook.toString());
        Book book = bookServices.addSearchingBook(patternBook);

        return "redirect:/book/show";
    }

    @GetMapping(value = "/getsummary")
    public String getSummary(@RequestParam("id") @NotNull Integer bookId, @RequestParam("name") @NotNull String bookName, Model model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }

            LocalDate subscriptionDate = currentUser.getSubscription();

            if (userService.hasRole(Role.ROLE_MODERATOR)) {

                if (subscriptionDate != null) {
                    LocalDate currentDate = LocalDate.now();
                    if ((currentDate.getYear() <= subscriptionDate.getYear()) && (currentDate.getDayOfYear() - subscriptionDate.getDayOfYear() > 30)) {
                        model.addAttribute("message", "Your subscription has expired!");
                        return "user/subscriptionform";
                    }
                } else {
                    model.addAttribute("message", "You do not have a subscription!");
                    return "user/subscriptionform";
                }
            }
        }

        String summary = bookServices.getBookSummary(bookId);
        String summaryName = bookName + " summary";
        model.addAttribute("summary", summary);
        model.addAttribute("name", summaryName);

        return "book/showsummary";
    }
}
