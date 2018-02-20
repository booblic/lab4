package lab4.library.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Controller
public class BookController {

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping(value = "/showbook", method = RequestMethod.GET)
    @ResponseBody
    public String showBooks() {
        Book book = (Book) entityManager.find(Book.class, 2);
        String string = book.getName() + " " + book.getIsbn() + " " + book.getYear();
        return string;
    }
}
