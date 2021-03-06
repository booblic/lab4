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
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Spring MVC controller for entity  book
 *
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
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/show")
    public String showBooks(Model model) {
        userService.fillHeader(model);
        model.addAttribute("books", bookServices.findAllBook());
        return "book/showallbooks";
    }

    /**
     * The method returns the name jsp to add a book
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getaddform")
    public String getAddForm(Model model) {
        userService.fillHeader(model);
        model.addAttribute("book", new FormBook());
        return "book/formaddbook";
    }

    /**
     * The method gets an object containing information about the book, renders it to the service level and returns the name jsp to display the books
     *
     * @param model    - defines a holder for model attributes
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
        Book book = bookServices.addOrEditBook(formBook);
        if (book.getFree() == true) {
            return "redirect:/book/showFree";
        } else {
            return "redirect:/book/show";
        }
    }

    /**
     * The method returns the name jsp to find the book by name
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getsearchingbookform")
    public String getSearchingForm(Model model) {
        userService.fillHeader(model);
        return "book/searchingbookform";
    }

    /**
     * The method gets books with given names, adds them to the holder for model attributes, and returns the name jsp to display books
     *
     * @param model    - defines a holder for model attributes
     * @param data - entry date
     * @return name jsp
     */
    @GetMapping(value = "/searchingBookByNameAndAuthor")
    public String searchingBookByNameAndAuthor(@RequestParam(value = "data") String data, Model model) {
        userService.fillHeader(model);
        Pattern pattern = Pattern.compile("^[А-ЯA-Zа-яa-z0-9]+");
        if (!pattern.matcher(data).find()) {
            model.addAttribute("error", "Неверный ввод! Допустимы только буквенные или цифровые символы.");
            return "book/showallbooks";
        }

        List<Book> booksByName = bookServices.findByBookName(data);
        List<Book> booksByAuthor = bookServices.findByAuthor(data);

        Set<Book> bookSet = new HashSet<>();
        bookSet.addAll(booksByName);
        bookSet.addAll(booksByAuthor);

        if (bookSet.size() != 0) {
            model.addAttribute("books", bookSet);
        } else {
            model.addAttribute("error", "Sorry, books with name " + data + " a not found");
        }
        return "book/showallbooks";
    }

    /**
     * The method returns the name jsp for editing the book
     *
     * @param bookId - id book
     * @param model  - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getformedit")
    public String getEditForm(@RequestParam("id") @NotNull Integer bookId, Model model) {
        userService.fillHeader(model);
        model.addAttribute("book", bookServices.findOne(bookId));
        model.addAttribute("summary", bookServices.getBookSummary(bookId));
        return "book/formeditbook";
    }

    /**
     * The method accesses the server to remove the book and returns the name jsp to display books
     *
     * @param bookId - id book
     * @return name jsp
     */
    @GetMapping("/deletebook")
    public String deleteBook(@RequestParam("id") @NotNull Integer bookId) {
        Book book = bookServices.findOne(bookId);
        LOG.info("msg:  bookServices.deleteBook({})", bookId);
        bookServices.deleteBook(bookId);
        if (book.getFree() == true) {
            return "redirect:/book/showFree";
        } else {
            return "redirect:/book/show";
        }
    }

    /**
     * The method fills the holder for model attributes and returns the name jsp to view information about the book
     *
     * @param bookId - id book
     * @param model  - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping("/getformviewbook")
    public String getFormViewBook(@RequestParam("id") @NotNull Integer bookId, Model model) {
        userService.fillHeader(model);
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
     *
     * @param formBook - book information object
     * @return name jsp
     */
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {

        LOG.info("msg: bookServices.editBook({})", formBook.toString());
        Book book = bookServices.addOrEditBook(formBook);
        if (book.getFree() == true) {
            return "redirect:/book/showFree";
        } else {
            return "redirect:/book/show";
        }
    }

    /**
     * The method accesses the service to create or edit a review and returns the name of jsp to display books
     *
     * @param id         - id book
     * @param textReview - review text
     * @param rating     - rating
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
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/genreandyearsearchingform")
    public String genreAndYearSearchingForm(Model model) {
        userService.fillHeader(model);
        return "book/genreandyearsearchingform";
    }

    /**
     * The method gets books with a given year and genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     *
     * @param genreName - genre name
     * @param year      - name of the book
     * @param model     - defines a holder for model attributes
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
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/authorandgenresearchingform")
    public String authorAndGenreSearchingForm(Model model) {
        userService.fillHeader(model);
        return "book/authorandgenreform";
    }

    /**
     * The method gets books with a given author and genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     *
     * @param firstName - author's name
     * @param lastName  - surname of the author
     * @param genreName - genre name
     * @param model     - defines a holder for model attributes
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
     *
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
     *
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getsearchbookinternetform")
    public String getFindForm(Model model) {
        userService.fillHeader(model);
        return "book/searchbookinternetform";
    }

    /**
     * The method calls the service to retrieve a list of books from an external source and returns the name jsp to display books
     *
     * @param model    - defines a holder for model attributes
     * @param bookName - name of the book
     * @return name jsp
     */
    @PostMapping(value = "/searchbookinternet")
    public String searchBookInternet(@RequestParam String bookName, Model model) {
        userService.fillHeader(model);

        LOG.info("bookServices.searchBookInternet({})", bookName);
        List<PatternBook> books = bookServices.searchBookInternet(bookName);

        if (books.size() != 0) {
            model.addAttribute("books", books);
        } else {
            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found on mybook.ru");
        }
        return "book/searchingbookinternetresult";
    }

    /**
     * The method accesses the service to add a book and returns the name jsp to display books
     *
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
            userService.fillHeader(model);
            if (!userService.isSubscribed(currentUser.getSubscription(), model)) {
                return "user/subscriptionform";
            }
        }
        Book book = bookServices.findOne(bookId);
        String bookDescription = book.getDescription();
        String summary = bookServices.getBookSummary(bookId);
        Integer viewsNum = book.getViewsNumber();
        if (viewsNum == null) {
            book.setViewsNumber(1);
        } else {
            book.setViewsNumber(++viewsNum);
        }
        bookServices.saveBook(book);
        model.addAttribute("description", bookDescription);
        model.addAttribute("summary", summary);
        model.addAttribute("name", bookName);

        return "book/showsummary";
    }

    @GetMapping(value = "/getfreesummary")
    public String getFreeSummary(@RequestParam("id") @NotNull Integer bookId, @RequestParam("name") @NotNull String bookName, Model model) {

        String summary = bookServices.getBookSummary(bookId);
        model.addAttribute("summary", summary);
        model.addAttribute("name", bookName);

        return "book/showsummary";
    }

    @GetMapping(value = "/downloadbook")
    public ResponseEntity<StreamingResponseBody> downloadBook(@RequestParam("id") @NotNull Integer id, Model model) {

        ResponseEntity<StreamingResponseBody> responseEntity = null;

        LOG.info("msg: bookServices.download({})", id);
        try {
            responseEntity = bookServices.download(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping(value = "/showFree")
    public String showFree(Model model) {
        userService.fillHeader(model);
        List<Book> bookList = bookServices.findAllBook().stream().filter(book -> book.getFree() == true).collect(Collectors.toList());

        model.addAttribute("books", bookList);
        return "book/freesummary";
    }
}
