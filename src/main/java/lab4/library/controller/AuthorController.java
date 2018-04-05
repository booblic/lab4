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

@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private UserService userService;

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

    @PostMapping(value = "/searchingbyauthor")
    public String searchingByAuthor(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String middleName, Model model) {

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
