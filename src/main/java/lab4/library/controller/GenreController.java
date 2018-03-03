package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.services.GenreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreServices genreServices;

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm() {
        return "genre/searchingform";
    }

    @PostMapping(value = "/search")
    public String searchBookByGenreName(@RequestParam String genreName, Model model) {
        Genre genre = genreServices.findByGenreName(genreName);
        model.addAttribute("books", genre.getBooks());
        return "book/showallbooks";
    }
}
