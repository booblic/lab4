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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

/**
 * Spring MVC controller для сущности book
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для book
     */
    @Autowired
    private BookServiceImpl bookServices;

    /**
     * Объект сервиса, реализующего бизнес логики для author
     */
    @Autowired
    private AuthorService authorService;

    /**
     * Объект сервиса, реализующего бизнес логики для review
     */
    @Autowired
    private ReviewServiceImp reviewService;

    /**
     * Объект сервиса, реализующего бизнес логики для publisher
     */
    @Autowired
    private PublisherService publisherService;

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * Объект сервиса, реализующего бизнес логики для genre
     */
    @Autowired
    private GenreService genreService;

    /**
     * Объект сервиса для конвертаций
     */
    @Autowired
    private ConversionService conversionService;

    /**
     * Метод получает все книги, добавляет их в holder for model attributes и возвращает имя jsp для отображения книг
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод возвращает имя jsp для добавления книги
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает объект, содержащий информацию о книги, перелдает ее на уровень сервисов и возвращает имя jsp для отображения книг
     * @param model - defines a holder for model attributes
     * @param formBook - объект, содержащий информацию о книги
     * @return имя jsp
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
     * Метод возвращает имя jsp для поиска книги по имени
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает книги с заданным именим, добавляет их в holder for model attributes и возвращает имя jsp для отображения книг
     * @param model - defines a holder for model attributes
     * @param bookName - имя книги
     * @return имя jsp
     */
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

    /**
     * Метод возвращает имя jsp для редактирования книги
     * @param bookId - id книги
     * @param model - defines a holder for model attributes
     * @return имя jsp
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

        return "book/formeditbook";
    }

    /**
     * Метод обращется к сервису для удаления книги и возвращает имя jsp для отображения книг
     * @param bookId - id книги
     * @return имя jsp
     */
    @GetMapping("/deletebook")
    public String deleteBook(@RequestParam("id") @NotNull Integer bookId) {

        LOG.info("msg:  bookServices.deleteBook({})", bookId);
        bookServices.deleteBook(bookId);

        return "redirect:/book/show";
    }

    /**
     * Метод заполняет holder for model attributes и возвращает имя jsp для просмотра информации о книги
     * @param bookId - id книги
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
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

    /**
     * Метод обращается к сервису для редактирования полученной книги и возвращает имя jsp для отображения книг
     * @param formBook - объект, содержащий информацию о книги
     * @return имя jsp
     */
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {

        LOG.info("msg: bookServices.editBook({})", formBook.toString());
        Book book = bookServices.addOrEditBook(formBook);

        return "redirect:/book/show";
    }

    /**
     * Метод обращается к сервису для создания или редактирования обзора и возвращает имя jsp для отображения книг
     * @param id - id книги
     * @param textReview - текст обзора
     * @param rating - оценка
     * @return имя jsp
     */
    @PostMapping(value = "/{id}/addreview")
    public String addReview(@PathVariable Integer id, @RequestParam String textReview, @RequestParam Integer rating) {

        LOG.info("msg: reviewService.addReview({}, {}, {})", id, textReview, rating);
        Review review = reviewService.addReview(id, textReview, rating);

        return "redirect:/book/show";
    }

    /**
     * Метод возвращает имя jsp для поиска книги по году и жанру
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает книги с заданным годом и жанром, добавляет их в holder for model attributes и возвращает имя jsp для отображения книг
     * @param genreName - имя жанра
     * @param year - имя книги
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @PostMapping(value = "/searchingbygenreandyear")
    public String searchingByGenreAndYear(@RequestParam String genreName, @RequestParam Integer year, Model model) {

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
     * Метод возвращает имя jsp для поиска книги по автору и жанру
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает книги с заданным автором и жанром, добавляет их в holder for model attributes и возвращает имя jsp для отображения книг
     * @param firstName - имя автора
     * @param lastName - фамилия автора
     * @param genreName - имя жанра
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
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

    /**
     * Метод обращается к сервису для получения файла с информацией о книгах
     * @param id - список id книг
     * @return файл с информацией о книгах
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
     * Метод возвращает имя jsp для поиска книги в интернете
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод обращется к сервису для получения списка книг из внешнего источника и возвращает имя jsp для отображения книг
     * @param model - defines a holder for model attributes
     * @param bookName - имя книги
     * @return имя jsp
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
     * Метод обращется к сервису чтобы добавить книгу и возвращает имя jsp для отображения книг
     * @param patternBook - обект, содержащий информацию о книге
     * @return имя jsp
     */
    @PostMapping(value = "/addsearchingbook")
    public String addSearchingBook(@ModelAttribute PatternBook patternBook) {

        LOG.info("bookServices.addSearchingBook({})", patternBook.toString());
        Book book = bookServices.addSearchingBook(patternBook);

        return "redirect:/book/show";
    }
}
