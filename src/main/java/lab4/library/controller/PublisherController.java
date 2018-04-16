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

/**
 * Spring MVC controller for entity publisher
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/publisher")
public class PublisherController {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);

    /**
     * The service object that implements the business logic for the publisher
     */
    @Autowired
    private PublisherServiceImpl publisherService;

    /**
     * A service object that implements business logic for the user
     */
    @Autowired
    private UserService userService;

    /**
     * The method returns the name jsp to search for books by the publisher
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
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

    /**
     * The method gets books with a given genre, adds them to the holder for model attributes, and returns the name jsp for displaying books
     * @param publisherName - publisher name
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searchingbypublisher")
    public String searchByPublisher(@RequestParam(value = "publisherName") String publisherName, Model model) {

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
