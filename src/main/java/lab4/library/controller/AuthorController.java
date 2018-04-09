package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.service.AuthorService;
import lab4.library.service.AuthorServiceImpl;
import lab4.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring MVC controller для сущности author
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для author
     */
    @Autowired
    private AuthorServiceImpl authorService;

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserService userService;

    /**
     * Метод возвращает имя jsp для поиска книг по автору
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @GetMapping(value = "/getsearchingbyauthorform")
    public String getSearchingByAuthorForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "author/searchingformbyauthor";
    }

    /**
     * Метод получает книги с заданным автором, добавляет их в holder for model attributes и возвращает имя jsp для отображжения книг
     * @param firstName - имя автора
     * @param lastName - фамилия автора
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @PostMapping(value = "/searchingbyauthor")
    public String searchingByAuthor(@RequestParam String firstName, @RequestParam String lastName, Model model) {

        LOG.info("msg: findByFirstNameAndLastName({}, {});", firstName, lastName);
        Author author = authorService.findByFirstNameAndLastName(firstName, lastName);

        if (author != null) {

            model.addAttribute("books", author.getBooks());

        } else {

            model.addAttribute("error", "Sorry, books by author " + firstName + " " + lastName + " a not found");
        }
        return "book/showallbooks";
    }
}
