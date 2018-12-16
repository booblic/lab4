package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.service.BookServiceImpl;
import lab4.library.service.StatisticService;
import lab4.library.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Spring MVC controller for the start page
 * @author Кирилл
 * @version 1.0
 */
@Controller
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * A service object that implements business logic for a book
     */
    @Autowired
    private BookServiceImpl bookServices;

    /**
     * The method returns the name of the jsp start page
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @RequestMapping(value = "/")
    public String startPageMessage(Model model) {

        userService.fillHeader(model);
        model.addAttribute("bookCount", bookServices.getBookCount());

        return "startpage";
    }

    /**
     * The method returns the name jsp to select the type of book search
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/searchbookoptions")
    public String searchBookOptions(Model model) {
        userService.fillHeader(model);
        return "searchingbook";
    }

    @PostMapping(value = "/success")
    public String successLogin(Model model) {

        model.addAttribute("message", "You have successfully logged into your account!");

        return "";
    }
}
