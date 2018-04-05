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

@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    private static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private UserService userService;

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
