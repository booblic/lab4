package lab4.library;

import lab4.library.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author VYZH
 * @since 02.03.2018
 */
@Controller
public class ParamsExampleController {

    private static final Logger LOG = LoggerFactory.getLogger(ParamsExampleController.class);

    @GetMapping("/params")
    public String index() {
        return "params_example";
    }

    @PostMapping("/params/single")
    public String paramsAsSingleArgument(@RequestParam String bookData) {
        LOG.info("paramsAsSingleArgument, data = {}", bookData);

        // todo: re-write to org.springframework.core.convert.converter.Converter
        for (String line : bookData.split("[\r\n]+")) {
            String[] s = line.split(";");
            Book book = new Book();
            book.setBookName(s[0]);
            book.setIsbn(s[1]);
            book.setYear(Integer.valueOf(s[2]));
            LOG.info("book = {}", book);
        }

        return "params_example";
    }

    @PostMapping("/params/arrays")
    public String paramsAsArrays(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year) {
        LOG.info("paramsAsArrays, bookName = {}, isbn = {}, year = {}", bookName, isbn, year);
        return "params_example";
    }
}
