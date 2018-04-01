package lab4.library.controller;

import lab4.library.ReflectionToString;
import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.controller.convert.FormBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.review.Review;
import lab4.library.service.*;
import lab4.library.user.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookServices bookServices;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/show")
    public String showBooks(Model model) {
        LOG.info("msg: model.addAttribute(\"books\", bookServices.findAllBook());");
        model.addAttribute("books", bookServices.findAllBook());
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }

    @GetMapping(value = "/getaddform")
    public String getAddForm(Model model) {
        LOG.info("Message: model.addAttribute(\"book\", new Book());");
        model.addAttribute("book", new Book());
        LOG.info("Msg: return \"book/formaddbook\";");
        return "book/formaddbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addbook")
    public String addBook(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year) {

        Set<Book> bookSet = bookServices.addBook(bookName, isbn, year);

        LOG.info("msg: bookServices.saveBook(bookSet);");
        bookServices.saveBooks(bookSet);
        LOG.info("return \"redirect:/book/show\";");

        return "redirect:/book/show";
    }

    @GetMapping(value = "/getsearchingform")
    public String getSearchingForm(Model model) {
        LOG.info("msg: model.addAttribute(\"book\", new Book());");
        model.addAttribute("book", new Book());
        LOG.info("msg: return \"book/searchingform\";");
        return "book/searchingform";
    }

    @PostMapping(value = "/searchingbybookname")
    public String searchingByBookName(@RequestParam String bookName, Model model) {
        LOG.info("msg: List<Book> bookList = bookServices.findByBookName(bookName);", bookName);
        List<Book> bookList = bookServices.findByBookName(bookName);
        if (bookList.size() != 0) {
            LOG.info("msg: if (bookList.size() != 0) { model.addAttribute(\"books\", bookList); }");
            model.addAttribute("books", bookList);
        } else {
            LOG.info("msg: if (bookList.size() == 0) { model.addAttribute(\"error\", \"Sorry, books with name \" + bookName + \" a not found.\"); }", bookName);
            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found.");
            model.addAttribute("bookName", bookName);
        }
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }

    @GetMapping(value = "/formedit")
    public String editForm(@RequestParam("id") @NotNull Integer bookId, Model model) {
        LOG.info("msg: Book book = bookServices.findBook(bookId);", bookId);
        Book book = bookServices.findBook(bookId);
        model.addAttribute("book", book);
        LOG.info("msg: return \"book/formeditbook\";");
        return "book/formeditbook";
    }

    @GetMapping("/deletebook")
    public String deletebook(@RequestParam("id") @NotNull Integer bookId, Model model) {
        LOG.info("msg:  if (kind.compareTo(\"Delete\") == 0) {  bookServices.deleteBook(bookId); }");
        bookServices.deleteBook(bookId);
        LOG.info("msg: model.addAttribute(\"books\", bookServices.findAllBook());");
        model.addAttribute("books", bookServices.findAllBook());
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }

    @GetMapping("/formviewbook")
    public String getFormviewbook(@RequestParam("id") @NotNull Integer bookId, Model model) {
        LOG.info("msg: Book book = bookServices.findBook(bookId);", bookId);
        Book book = bookServices.findBook(bookId);
        LOG.info("msg:  if (kind.compareTo(\"View\") == 0) { Set<Review> reviews = book.getReviews(); }");
        Set<Review> reviews = book.getReviews();
        LOG.info("msg: model.addAttribute(\"reviews\", reviews);");
        model.addAttribute("reviews", reviews);
        LOG.info("msg: model.addAttribute(\"user\", userService.getCurrentUser());");
        model.addAttribute("user", userService.getCurrentUser());
        LOG.info("msg: return \"book/formviewbook\";");
        model.addAttribute("book", book);
        return "book/formviewbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/editbook")
    public String editBook(@ModelAttribute FormBook formBook) {
        LOG.info("msg: Book book = conversionService.convert(formBook, Book.class); " + ReflectionToString.reflectionToString(formBook));
        Book book = conversionService.convert(formBook, Book.class);
        LOG.info("msg: bookServices.editBook(book); " + ReflectionToString.reflectionToString(book));
        bookServices.editBook(book);
        LOG.info("msg: return \"redirect:/book/show\";");
        return "redirect:/book/show";
    }

    @PostMapping(value = "/{id}/addreview")
    public String addReview(@PathVariable Integer id, @RequestParam String textReview, @RequestParam String rating) {

        Review review = reviewService.addReview(id, textReview, rating);
        LOG.info("msg: reviewService.saveReview(review);");
        reviewService.saveReview(review);
        LOG.info("msg: return \"redirect:/book/show\";");
        return "redirect:/book/show";
    }

    @GetMapping(value = "/genreandyearsearchingform")
    public String genreAndYearSearchingForm() {
        LOG.info("msg: return \"book/genreandyearsearchingform\";");
        return "book/genreandyearsearchingform";
    }

    @PostMapping(value = "/searchingbygenreandyear")
    public String searchingByGenreAndYear(@RequestParam String genreName, @RequestParam int year, Model model) {
        LOG.info("msg: model.addAttribute(\"books\", bookServices.findByYearAndGenreName(genreName, year));", genreName, year);
        model.addAttribute("books", bookServices.findByYearAndGenreName(genreName, year));
        model.addAttribute("bookName", "");
        LOG.info("msg: return \"book/showallbooks\";");
        return "book/showallbooks";
    }

    @GetMapping(value = "/authorandgenresearchingform")
    public String authorAndGenreSearchingForm() {
        LOG.info("msg: return \"book/authorandgenreform\";");
        return "book/authorandgenreform";
    }

    @PostMapping(value = "/searhcingbyauthorandgenre")
    public String searchingByAuthorAndGenre(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String genreName, Model model) {
        LOG.info("msg: model.addAttribute(\"books\", bookServices.findByAuthorAndGenreName(firstName, lastName, genreName));", firstName, lastName, genreName);
        model.addAttribute("books", bookServices.findByAuthorAndGenreName(firstName, lastName, genreName));
        model.addAttribute("bookName", "");
        return "book/showallbooks";
    }

    @PostMapping(value = "/exportbooks")
    public ResponseEntity<StreamingResponseBody> exportBooks(@RequestParam List<Integer> id) throws IOException {

        List<Book> books = bookServices.findAll(id);

        final File csvFile = File.createTempFile("book_csv", "tmp");

        String lineSeparator = System.getProperty("line.separator");

        try (Writer writer = new FileWriter(csvFile)) {
            for (Book book : books) {
                writer.append(book.getBookName())
                        .append(",")
                        .append(book.getIsbn())
                        .append(",")
                        .append(String.valueOf(book.getYear()))
                        .append(lineSeparator);
            }
        } catch (IOException exception) {
            LOG.error("printStackTrace()", exception);
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv").body((OutputStream outputStream) -> {
            StreamUtils.copy(
                    new BufferedInputStream(new FileInputStream(csvFile)),
                    outputStream
            );
            csvFile.delete();
        });


        //ResponseEntity<StreamingResponseBody> responseEntity = new ResponseEntity<>(streamingResponseBody, httpHeaders);

        /*if (books.size() != 0) {

            for (Book book : books) {
                LOG.info("msg: stringBuilder.append(book.getBookName()).append(\",\").append(book.getIsbn()).append(\",\").append(book.getYear()).append(lineSeparator);", book);
                stringBuilder.append(book.getBookName()).append(",").append(book.getIsbn()).append(",").append(book.getYear()).append(lineSeparator);
            }

        }

        LOG.info("msg: Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(\"books.csv\"), \"utf-8\"));");
        try(Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("books.csv"), "utf-8"));) {

            LOG.info("msg: file.write(stringBuilder.toString());", stringBuilder.toString());
            file.write(stringBuilder.toString());

        } catch (IOException exception) {
            LOG.error("msg: exception.printStackTrace();", exception);
            exception.printStackTrace();
        }

        LOG.info("msg: HttpHeaders httpHeaders = new HttpHeaders();");
        HttpHeaders httpHeaders = new HttpHeaders();
        LOG.info("httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=books.csv\");");
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv");

        LOG.info("msg: return new HttpEntity<>(Files.readAllBytes(Paths.get(\"books.csv\")), httpHeaders);");
        return new HttpEntity<>(Files.readAllBytes(Paths.get("books.csv")), httpHeaders);*/
    }

    @GetMapping(value = "/getfindbookform")
    public String getFindForm() {
        return "book/findbookform";
    }

    @PostMapping(value = "/findbook")
    public String findBook(@RequestParam String bookName, Model model) {

        List<PatternBook> books = new ArrayList<>();

        Map<String, String> vars = new HashMap<>();
        vars.put("name", bookName);

        String result = restTemplate.getForObject("https://mybook.ru/search/books/?q={name}", String.class, vars);

        Document document = Jsoup.parse(result);

        Elements searchResultItemInfos = document.getElementsByClass("search-result__item-info");

        int count = 1;

        for (Element searchResultItemInfo : searchResultItemInfos) {

            PatternBook book = new PatternBook();

            Elements searchResultItemInfoAuthors = searchResultItemInfo.getElementsByClass("search-result__item-info_author");

            Elements searchResultItemMores = searchResultItemInfo.getElementsByClass("search-result__item_more");

            for (Element searchResultItemInfoAuthor : searchResultItemInfoAuthors) {
                book.setBookName(searchResultItemInfoAuthor.text());
                book.setHref("https://mybook.ru/" + searchResultItemInfoAuthor.attr("href"));
                book.setCount(count++);
            }

            for (Element searchResultItemMore : searchResultItemMores) {

                Elements elementsTagPs = searchResultItemMore.getElementsByTag("p");

                for (Element elementsTagP : elementsTagPs) {
                    String info = elementsTagP.text();

                    if (info.toLowerCase().contains("Год издания:".toLowerCase())) {
                        book.setYear(info.replace("Год издания:", "").trim());
                    }

                    if (info.toLowerCase().contains("Правообладатель:".toLowerCase())) {
                        book.setPublishersNames(info.replace("Правообладатель:", "").trim());
                    }

                    if (info.toLowerCase().contains("Жанр:".toLowerCase())) {
                        book.setGenresNames(info.replace("Жанр:", "").trim());
                    }

                    Elements elementsTagAs = elementsTagP.getElementsByTag("a");

                    for (Element elementsTagA : elementsTagAs) {

                        Elements authorsNames = elementsTagA.getElementsByAttributeValueMatching("href", "/author/");

                        for (Element authorName : authorsNames) {
                            if (book.getAuthorsNames() != null) {
                                book.setAuthorsNames(book.getAuthorsNames() + ", " + authorName.text());
                            } else {
                                book.setAuthorsNames(authorsNames.text());
                            }
                        }
                    }
                }
            }
            books.add(book);
        }
        model.addAttribute("books", books);

        return "book/findingresult";
    }

    @PostMapping(value = "addfindingbook")
    public String addFindingBook(@ModelAttribute PatternBook patternBook) {

        Document doc = null;

        try {
            doc = Jsoup.connect(patternBook.getHref()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements bookSummarys = doc.getElementsByClass("book-summary");

        for (Element bookSummary : bookSummarys) {
            Elements items = bookSummary.getElementsByClass("item");

            for (Element item : items) {
                if (item.text().contains("ISBN (EAN):")) {
                    patternBook.setIsbn(item.text().replace("ISBN (EAN):", "").trim());
                }
            }
        }

        Book book = bookServices.addFindingBook(patternBook);

        bookServices.saveBook(book);

        return "redirect:/book/show";
    }
}
