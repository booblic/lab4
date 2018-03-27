package lab4.library.controller;

import lab4.library.author.Author;
import lab4.library.service.AuthorService;
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
    private AuthorService authorService;

    @GetMapping(value = "/getsearchingbyauthorform")
    public String getSearchingByAuthorForm() {
        LOG.info("msg: return \"author/searchingform\";");
        return "author/searchingform";
    }

    @PostMapping(value = "/searchingbyauthor")
    public String searchingByAuthor(@RequestParam String firstName, @RequestParam String lastName, Model model) {
        LOG.info("msg: Author author = authorService.findByFirstNameAndLastName(firstName, lastName);", firstName, lastName);
        Author author = authorService.findByFirstNameAndLastName(firstName, lastName);
        if (author != null) {
            LOG.info("msg: if (author != null) { model.addAttribute(\"books\", author.getBooks()); }");
            model.addAttribute("books", author.getBooks());
        } else {
            LOG.info("msg: if (author == null) { model.addAttribute(\"error\", \"Sorry, books by author \" + firstName + \" \" + lastName + \" a not found.\"); }", lastName, firstName);
            model.addAttribute("error", "Sorry, books by author " + firstName + " " + lastName + " a not found.");
            model.addAttribute("bookName", "");
        }
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }
}
