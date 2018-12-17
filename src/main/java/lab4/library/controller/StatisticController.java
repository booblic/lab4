package lab4.library.controller;

import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.service.BookServiceImpl;
import lab4.library.service.StatisticService;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatisticController {

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private BookServiceImpl bookService;


    @GetMapping(value = "/gettopform")
    public String getTopForm(Model model) {
        userService.fillHeader(model);
        List<Book> books = statisticService.findTopViews();
        model.addAttribute("topSummary", books);
        model.addAttribute("topFreeSummary", books.stream().filter(book -> book.getFree() == true).collect(Collectors.toList()));
        Map<String, Integer> unsortedTopGenres = bookService.findAllBook().stream().collect(HashMap::new, (map, book) -> {
                    Set<Genre> genres = book.getGenres();
                    if (genres.size() != 0) {
                        for (Genre genre : genres) {
                            String genreName = genre.getGenreName();
                            if (map.containsKey(genreName))
                                map.put(genreName, map.get(genreName) + book.getViewsNumber());
                            else
                                map.put(genreName, book.getViewsNumber());
                        }
                    }
                }, HashMap::putAll
        );
        Map<String, Integer> topGenres = new LinkedHashMap<>();
        unsortedTopGenres.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> topGenres.put(x.getKey(), x.getValue()));
        model.addAttribute("topGenres", topGenres);
        Integer currentSubscribersNumber = userService.getAllUsers().stream().map(user -> {
            LocalDate subscriptionDate = user.getSubscription();
            if (userService.hasRole(Role.ROLE_USER)) {
                if (subscriptionDate != null) {
                    LocalDate currentDate = LocalDate.now();
                    if ((currentDate.getYear() <= subscriptionDate.getYear()) && (currentDate.getDayOfYear() - subscriptionDate.getDayOfYear() > 30)) {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
            return 1;
        }).reduce((x , y) -> x + y).orElse(0);
        model.addAttribute("currentSubscribersNumber", currentSubscribersNumber);
        Integer commonViewNumber = books.stream().map(book -> book.getViewsNumber()).reduce((x, y) -> x + y).orElse(0);
        model.addAttribute("commonViewNumber", commonViewNumber);
        return "topforsales";
    }
}
