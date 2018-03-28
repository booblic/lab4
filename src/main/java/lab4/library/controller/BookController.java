package lab4.library.controller;

import lab4.library.ReflectionToString;
import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.controller.convert.FormBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.review.Review;
import lab4.library.service.AuthorService;
import lab4.library.service.BookServices;
import lab4.library.service.GenreService;
import lab4.library.service.PublisherService;
import lab4.library.service.ReviewService;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping(value = "/getaddform")
    public String getAddForm(@RequestParam String bookName, Model model) {
        LOG.info("Message: model.addAttribute(\"book\", new Book());");
        model.addAttribute("book", new Book());
        model.addAttribute("bookName", bookName);
        LOG.info("Msg: return \"book/formaddbook\";");
        return "book/formaddbook";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addbook")
    public String addBook(@RequestParam String[] bookName, @RequestParam String[] isbn, @RequestParam Integer[] year) {

        Set<Book> bookSet = new HashSet<>();

        for (int i = 0; i < bookName.length; i++) {
            Book book = new Book();
            book.setBookName(bookName[i]);
            book.setIsbn(isbn[i]);
            book.setYear(year[i]);
            LOG.info("Add book in Set: " + ReflectionToString.reflectionToString(book));
            bookSet.add(book);
        }
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

    @PostMapping(value = "/search")
    public String searchBookByBookName(@RequestParam String bookName, Model model) {
        LOG.info("msg: List<Book> bookList = bookServices.findByBookName(bookName);", bookName);
        List<Book> bookList = bookServices.findByBookName(bookName);
        if (bookList.size() != 0) {
            LOG.info("msg: if (bookList.size() != 0) { model.addAttribute(\"books\", bookList); }");
            model.addAttribute("books", bookList);
        } else {
            LOG.info("msg: if (bookList.size() == 0) { model.addAttribute(\"error\", \"Sorry, books with name \"{}\" a not found.\"); }", bookName);
            model.addAttribute("error", "Sorry, books with name " + bookName + " a not found.");
            model.addAttribute("bookName", bookName);
        }
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

    @PostMapping(value = "/formedit")
    public String editForm(@RequestParam Integer bookId, @RequestParam String kind, Model model) {
        LOG.info("msg: Book book = bookServices.findBook(bookId);", bookId);
        Book book = bookServices.findBook(bookId);
        model.addAttribute("book", book);

//        if (kind.compareTo("View") == 0) {
//            LOG.info("msg:  if (kind.compareTo(\"View\") == 0) { Set<Review> reviews = book.getReviews(); }");
//            Set<Review> reviews = book.getReviews();
//            LOG.info("msg: model.addAttribute(\"reviews\", reviews);");
//            model.addAttribute("reviews", reviews);
//            LOG.info("msg: model.addAttribute(\"user\", userService.getCurrentUser());");
//            model.addAttribute("user", userService.getCurrentUser());
//            LOG.info("msg: return \"book/formviewbook\";");
//            return "book/formviewbook";
//        }

        if (kind.compareTo("Delete") == 0) {
            LOG.info("msg:  if (kind.compareTo(\"Delete\") == 0) {  bookServices.deleteBook(bookId); }");
            bookServices.deleteBook(bookId);
            LOG.info("msg: model.addAttribute(\"books\", bookServices.findAllBook());");
            model.addAttribute("books", bookServices.findAllBook());
            LOG.info("msg: return \"book/showallbooks\";");
            return "book/showallbooks";
        }
        LOG.info("msg: return \"book/formeditbook\";");
        return "book/formeditbook";
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

        Review review = null;
        LOG.info("msg: User user = userService.getCurrentUser();");
        User user = userService.getCurrentUser();
        LOG.info("msg: Book book = bookServices.findBook(id);", id);
        Book book = bookServices.findBook(id);

        Map<String, String> bookReview = new HashMap<>();
        LOG.info("msg: bookReview.put(textReview, rating);", textReview, rating);
        bookReview.put(textReview, rating);

        if (reviewService.findByBookAndUser(book, user) != null) {
            LOG.info("msg: if (reviewService.findByBookAndUser(book, user) != null) { review.setBookReview(bookReview); }", bookReview);
            review = reviewService.findByBookAndUser(book, user);
            review.setBookReview(bookReview);
        } else {
            review = new Review();
            LOG.info("if (reviewService.findByBookAndUser(book, user) == null) { review.setUser(user); review.setBook(book); review.setBookReview(bookReview); }");
            review.setUser(user);
            review.setBook(book);
            review.setBookReview(bookReview);
        }
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
/*        LOG.info("msg: Book book = new Book();");
        Book book = new Book();
        LOG.info("msg:  book.setYear(year);", year);
        book.setYear(year);
        Set<Genre> genreSet = new HashSet<>();
        LOG.info("msg:  genreSet.add(genreService.findByGenreName(genreName));", genreName);
        genreSet.add(genreService.findByGenreName(genreName));
        LOG.info("msg:  book.setGenres(genreSet);");
        book.setGenres(genreSet);
        LOG.info("msg:  Example<Book> example = Example.of(book, ExampleMatcher.matching().withIgnorePaths(\"bookRating\"));");
        Example<Book> example = Example.of(book, ExampleMatcher.matching().withIgnorePaths("bookRating"));*/
        LOG.info("msg: model.addAttribute(\"books\", bookServices.findByYearAndGenreName(genreName, year));", genreName, year);
        model.addAttribute("books", bookServices.findByYearAndGenreName(genreName, year));
        model.addAttribute("bookName", "");
/*        LOG.info("msg: model.addAttribute(\"books\", bookServices.findBook(example));", genreName, year);
        model.addAttribute("books", bookServices.findBook(example));*/
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

    @GetMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("value", "test js");
        return "book/testjsp";
    }

    @PostMapping(value = "/params/arrays")
    public String paramsAsArrays(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String genreName, Model model) {
        model.addAttribute("books", bookServices.findByAuthorAndGenreName(firstName, lastName, genreName));
        return "book/showallbooks";
    }

    @PostMapping(value = "/exportbooks")
    public StreamingResponseBody exportBooks(@RequestParam List<Integer> id) throws IOException {
        // todo: throw exception if id is empty

        LOG.info("msg: List<Book> books = new ArrayList<>();");
        List<Book> books = bookServices.findAll(id);

        LOG.info("msg: StringBuilder stringBuilder = new StringBuilder();");
        final File csvFile = File.createTempFile("book_csv", "tmp");

        LOG.info("msg: String lineSeparator = System.getProperty(\"line.separator\");");
        String lineSeparator = System.getProperty("line.separator");

        LOG.info("msg: Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(\"books.csv\"), \"utf-8\"));");
        try(Writer out = new FileWriter(csvFile)) {
            for (Book book : books) {
                LOG.info("msg: stringBuilder.append(book.getBookName()).append(\",\").append(book.getIsbn()).append(\",\").append(book.getYear()).append(lineSeparator);", book);
                out.append(book.getBookName())
                        .append(",")
                        .append(book.getIsbn())
                        .append(",")
                        .append(String.valueOf(book.getYear()))
                        .append(lineSeparator);
            }
        } catch (IOException exception) {
            LOG.error("msg: exception.printStackTrace();", exception);
        }

        LOG.info("msg: HttpHeaders httpHeaders = new HttpHeaders();");
        HttpHeaders httpHeaders = new HttpHeaders();

        LOG.info("httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=books.csv\");");
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv");

        LOG.info("msg: return new HttpEntity<>(Files.readAllBytes(Paths.get(\"books.csv\")), httpHeaders);");
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                StreamUtils.copy(
                        new BufferedInputStream(new FileInputStream(csvFile)),
                        outputStream
                );
                csvFile.delete();
            }
        };
    }

    @PostMapping(value = "/getfindbookform")
    public String getFindForm(@RequestParam String bookName, Model model) {
        model.addAttribute("bookName", bookName);
        return "book/findbookform";
    }

    @PostMapping(value = "/findbook")
    public String findBook(@RequestParam String bookName, Model model) throws Exception {

        String lineSeparator = System.getProperty("line.separator");

        List<PatternBook> books = new ArrayList<>();

//jsoup get
/*      try {
            Document doc  = Jsoup.connect("https://mybook.ru/search/books/?q=" + bookName).get();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

//restTemplate get
        Map<String, String> vars = new HashMap<>();
        vars.put("name", bookName);

        String result = restTemplate.getForObject(
                "https://mybook.ru/search/books/?q={name}", String.class, vars);
/*        String line = null;
        String result = null;


        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt"), "windows-1251"))) {

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }*/

        Document document = Jsoup.parse(result);

        //Elements SearchResultItemSearchResultItemBookFirstChilds = document.getElementsByClass("search-result__item search-result__item_book first_child");
        Elements searchResultItemInfos = document.getElementsByClass("search-result__item-info");

        int count = 1;

        for (Element searchResultItemInfo: searchResultItemInfos) {

            PatternBook book = new PatternBook();

            Elements searchResultItemInfoAuthors = searchResultItemInfo.getElementsByClass("search-result__item-info_author");

            Elements searchResultItemMores = searchResultItemInfo.getElementsByClass("search-result__item_more");

            for (Element searchResultItemInfoAuthor: searchResultItemInfoAuthors) {


                /*System.out.println("-------------------------------------------");
                System.out.println(searchResultItemInfoAuthor.text());
                System.out.println(searchResultItemInfoAuthor.attr("href"));
                System.out.println("-------------------------------------------");*/

                book.setBookName(searchResultItemInfoAuthor.text());
                book.setHref("https://mybook.ru/" + searchResultItemInfoAuthor.attr("href"));
                book.setCount(count++);
            }

            for (Element searchResultItemMore: searchResultItemMores) {

                Elements elementsTagPs = searchResultItemMore.getElementsByTag("p");

                for (Element elementsTagP: elementsTagPs) {
                    String info = elementsTagP.text();

                    if (info.toLowerCase().contains("Год издания:".toLowerCase())) {
                        /*System.out.println("-------------------------------------------");
                        System.out.println(info);
                        System.out.println("-------------------------------------------");*/

                        book.setYear(info.replace("Год издания:", "").trim());
                    }

                    if (info.toLowerCase().contains("Правообладатель:".toLowerCase())) {
/*                        System.out.println("-------------------------------------------");
                        System.out.println(info);
                        System.out.println("-------------------------------------------");*/
                        book.setPublishersNames(info.replace("Правообладатель:", "").trim());
                    }

                    if (info.toLowerCase().contains("Жанр:".toLowerCase())) {
/*                        System.out.println("-------------------------------------------");
                        System.out.println(info);
                        System.out.println("-------------------------------------------");*/
                        book.setGenresNames(info.replace("Жанр:", "").trim());
                    }

                    Elements elementsTagAs = elementsTagP.getElementsByTag("a");

                    for (Element elementsTagA: elementsTagAs) {

                        Elements authorsNames = elementsTagA.getElementsByAttributeValueMatching("href", "/author/");

                        for (Element authorName: authorsNames) {
/*                            System.out.println("-------------------------------------------");
                            System.out.println(authorName.text());
                            System.out.println("-------------------------------------------");*/
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

/*            System.out.println("-------------------------------------------");
            System.out.println(ReflectionToString.reflectionToString(b1));
            System.out.println("-------------------------------------------");*/

        }

/*        int count = 0;

        for (Element SearchResultItemSearchResultItemBookFirstChild: SearchResultItemSearchResultItemBookFirstChilds) {

            Elements booksItemInfoNames = SearchResultItemSearchResultItemBookFirstChild.getElementsByClass("books__item__info-name");

            Elements booksItemInfoAuthors = SearchResultItemSearchResultItemBookFirstChild.getElementsByClass("books__item__info-authors");

            Elements searchResultItemMores = SearchResultItemSearchResultItemBookFirstChild.getElementsByClass("search-result__item_more");

            PatternBook b = new PatternBook();

            count++;

            for (Element booksItemInfoName: booksItemInfoNames) {
                Elements elementsTagAs = booksItemInfoName.getElementsByTag("a");

                for (Element elementsTagA: elementsTagAs) {
                    String book = elementsTagA.text();
                    b.setCount(count);
                    b.setBookName(book);

                }
            }

            for (Element booksItemInfoAuthor: booksItemInfoAuthors) {
                Elements elementsTagAs = booksItemInfoAuthor.getElementsByTag("a");

                for (Element elementsTagA: elementsTagAs) {
                    String author = elementsTagA.text();

                    //b.setAuthorsNames(author);

                }
            }

            for (Element searchResultItemMore: searchResultItemMores) {

                Elements elementsTagPs = searchResultItemMore.getElementsByTag("p");

                for (Element elementsTagP: elementsTagPs) {
                    String info = elementsTagP.text();

                    if (info.toLowerCase().contains("Год издания".toLowerCase())) {
                        b.setYear(info);
                    }

                    if (info.toLowerCase().contains("Правообладатель".toLowerCase())) {
                        b.setPublishersNames(info);
                    }

                    if (info.toLowerCase().contains("Жанр".toLowerCase())) {
                        b.setGenresNames(info);
                    }

                    Elements elementsTagAs = elementsTagP.getElementsByTag("a");

                    for (Element elementsTagA: elementsTagAs) {

                        Elements authorsNames = elementsTagA.getElementsByAttributeValueMatching("href", "/author/");

                        for (Element authorName: authorsNames) {
                            if (b.getAuthorsNames() != null) {
                                b.setAuthorsNames(b.getAuthorsNames() + ", " + authorName.text());
                            } else {
                                b.setAuthorsNames(authorsNames.text());
                            }
                        }
                    }
                }
            }
            books.add(b);
        }*/

/*        try(Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("import.txt", true), "utf-8"));) {

        } catch (IOException exception) {
            exception.printStackTrace();
        }*/

        model.addAttribute("books", books);

        return "book/findingresult";
    }

    @PostMapping(value = "addfindingbook")
    public String addFindingBook(@ModelAttribute PatternBook patternBook, Model model) {

        Book book = new Book();

        book.setBookName(patternBook.getBookName());

        if (patternBook.getYear() != null) {
            Integer year = Integer.parseInt(patternBook.getYear());
            book.setYear(year);
        }

        if (patternBook.getAuthorsNames() != null) {
            Set<Author> authorSet = new HashSet<>();
            for (String authorName: patternBook.getAuthorsNames().trim().split(",")) {
                String[] authorNameMassif = new String[2];
                int i = 0;
                for (String name: authorName.trim().split(" ")) {
                    authorNameMassif[i] = name;
                    i++;
                }
                Author existingAuthor = authorService.findByFirstNameAndLastName(authorNameMassif[0], authorNameMassif[1]);

                if (existingAuthor != null) {
                    authorSet.add(existingAuthor);
                } else {
                    Author author = new Author();
                    author.setFirstName(authorNameMassif[0]);
                    author.setLastName(authorNameMassif[1]);
                    authorSet.add(authorService.saveAuthor(author));
                }
            }
            book.setAuthors(authorSet);
        }

        if (patternBook.getGenresNames() != null) {
            Set<Genre> genreSet = new HashSet<>();
            for (String genreName: patternBook.getGenresNames().split(",")) {
                Genre existingGenre = genreService.findByGenreName(genreName.trim());
                if (existingGenre != null) {
                    genreSet.add(existingGenre);
                } else {
                    Genre genre = new Genre();
                    genre.setGenreName(genreName.trim());
                    genreSet.add(genreService.saveGenre(genre));
                }
            }
            book.setGenres(genreSet);
        }

        if (patternBook.getPublishersNames() != null) {
            Set<Publisher> publisherSet = new HashSet<>();
            for (String publisherName: patternBook.getPublishersNames().split(",")) {
                Publisher existingPublisher = publisherService.findByPublisherName(publisherName.trim());
                if (existingPublisher != null) {
                    publisherSet.add(existingPublisher);
                } else {
                    Publisher publisher = new Publisher();
                    publisher.setPublisherName(publisherName.trim());
                    publisherSet.add(publisherService.savePublisher(publisher));
                }
            }
            book.setPublishers(publisherSet);
        }

        Document doc = null;

        try {
            doc  = Jsoup.connect(patternBook.getHref()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements bookSummarys = doc.getElementsByClass("book-summary");

        for (Element bookSummary: bookSummarys) {
            Elements items = bookSummary.getElementsByClass("item");

            for (Element item: items) {
                if (item.text().contains("ISBN (EAN):")) {
                    book.setIsbn(item.text().replace("ISBN (EAN):", "").trim());
                }
            }
        }


        bookServices.saveBook(book);

        return "redirect:/book/show";
    }
}
