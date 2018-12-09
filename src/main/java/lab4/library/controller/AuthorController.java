package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.service.AuthorServiceImpl;
import lab4.library.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring MVC controller for entity author
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Object of service that implements business logic for author
     */
    @Autowired
    private AuthorServiceImpl authorService;

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * The method returns the name jsp for searching books by author
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getsearchingbyauthorform")
    public String getSearchingByAuthorForm(Model model) {
        userService.fillHeader(model);
        return "author/searchingformbyauthor";
    }

    /**
     * The method receives books with the given author, adds them to the holder for model attributes, and returns the name jsp for displaying books
     * @param firstName - name of the author
     * @param lastName - surname of the author
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searchingbyauthor")
    public String searchingByAuthor(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName, Model model) {

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
