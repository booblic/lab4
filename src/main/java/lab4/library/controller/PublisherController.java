package lab4.library.controller;

import lab4.library.publisher.Publisher;
import lab4.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm() {
        return "publisher/searchingform";
    }

    @PostMapping(value = "/search")
    public String searchBookByPublisherName(@RequestParam String publisherName, Model model) {
        Publisher publisher = publisherService.findByPublisherName(publisherName);
        if (publisher != null) {
            model.addAttribute("books", publisher.getBooks());
        } else {
            model.addAttribute("error", "Sorry, books by publisher " + publisherName + " a not found.");
        }
        return "book/showallbooks";
    }
}
