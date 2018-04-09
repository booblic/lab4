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
 * Spring MVC controller для сущности publisher
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/publisher")
public class PublisherController {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для publisher
     */
    @Autowired
    private PublisherServiceImpl publisherService;

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserService userService;

    /**
     * Метод возвращает имя jsp для поиска книг по издателю
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает книги с заданным жанром, добавляет их в holder for model attributes и возвращает имя jsp для отображжения книг
     * @param publisherName - имя издателя
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
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
