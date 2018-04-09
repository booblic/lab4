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
 * Spring MVC controller для сущности genre
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    private static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для genre
     */
    @Autowired
    private GenreServiceImpl genreService;

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserService userService;

    /**
     * Метод возвращает имя jsp для поиска книг по жанру
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает книги с заданным жанром, добавляет их в holder for model attributes и возвращает имя jsp для отображжения книг
     * @param genreName - имя жанра
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
