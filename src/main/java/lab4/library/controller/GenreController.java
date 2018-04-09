package lab4.library.controller;

import lab4.library.genre.Genre;
import lab4.library.service.GenreServiceImpl;
import lab4.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Spring MVC controller for the essence of genre
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    private static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

    /**
     * The object of the service that implements the business logic for the genre
     */
    @Autowired
    private GenreServiceImpl genreService;

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserService userService;

    /**
     * The method returns the name jsp to search for books by genre
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getsearchingbygenreform")
    public String getSearchingByGenreForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "genre/searchingformbygenre";
    }

    /**
     * The method gets books with a given genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     * @param genreName - genre name
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @PostMapping(value = "/searchingbygenre")
    public String searchingByGenre(@RequestParam String genreName, Model model) {

        LOG.info("msg: genreService.findByGenreName({})", genreName);
        Genre genre = genreService.findByGenreName(genreName);

        if (genre != null) {

            model.addAttribute("books", genre.getBooks());

        } else {

            model.addAttribute("error", "Sorry, books of the genre " + genreName + " a not found");
        }
        return "book/showallbooks";
    }
}
