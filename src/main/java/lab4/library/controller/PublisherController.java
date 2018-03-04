package lab4.library.controller;

import lab4.library.publisher.Publisher;
import lab4.library.service.PublisherServices;
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
    private PublisherServices publisherServices;

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm() {
        return "publisher/searchingform";
    }

    @PostMapping(value = "/search")
    public String searchBookByPublisherName(@RequestParam String publisherName, Model model) {
        Publisher publisher = publisherServices.findByPublisherName(publisherName);
        model.addAttribute("books", publisher.getBooks());
        return "book/showallbooks";
    }
}
