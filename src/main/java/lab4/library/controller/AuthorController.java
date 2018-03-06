package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.service.AuthorService;
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

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm() {
        return "author/searchingform";
    }

    @PostMapping(value = "/search")
    public String serchBookByAuthorName(@RequestParam String firstName, @RequestParam String lastName, Model model) {
        Author author = authorService.findByFirstNameAndLastName(firstName, lastName);
        model.addAttribute("books", author.getBooks());
        return "/book/showallbooks";
    }
}
