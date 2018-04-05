package lab4.library.controller;

import lab4.library.publisher.Publisher;
import lab4.library.service.PublisherService;
import lab4.library.service.PublisherServiceImpl;
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
@RequestMapping(value = "/publisher")
public class PublisherController {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);

    @Autowired
    private PublisherServiceImpl publisherService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getsearchingbypublisherform")
    public String getSearchingByPublisherForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "publisher/searchingformbypublisher";
    }

    @PostMapping(value = "/searchingbypublisher")
    public String searchByPublisher(@RequestParam String publisherName, Model model) {

        LOG.info("msg: publisherService.findByPublisherName({})", publisherName);
        Publisher publisher = publisherService.findByPublisherName(publisherName);

        if (publisher != null) {

            model.addAttribute("books", publisher.getBooks());

        } else {

            model.addAttribute("error", "Sorry, books by publisher " + publisherName + " a not found");
        }
        return "book/showallbooks";
    }
}
