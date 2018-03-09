package lab4.library.controller;

import lab4.library.exception.ResourceNotFoundException;
import lab4.library.genre.Genre;
import lab4.library.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm() {
        return "genre/searchingform";
    }

    @PostMapping(value = "/search")
    public String searchBookByGenreName(@RequestParam String genreName, Model model) {
        Genre genre = genreService.findByGenreName(genreName);
        if (genre != null) {
            model.addAttribute("books", genre.getBooks());
        } else {
            //model.addAttribute("error", "Sorry, books of the genre " + genreName + " a not found.");
            throw new ResourceNotFoundException();
        }
        return "book/showallbooks";
    }
}
