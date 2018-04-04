package lab4.library.service;

import lab4.library.ReflectionToString;
import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.controller.convert.FormBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.*;

@Service
public class BookServices {

    private static final Logger LOG = LoggerFactory.getLogger(BookServices.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private RestTemplate restTemplate;

    public Book findOne(Integer id) {
        return bookRepository.findOne(id);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> findBookByIterableId(Iterable<Integer> id) {
        return bookRepository.findAll(id);
    }

    public List<Book> findByYearAndGenreName(String genreName, int year) {
        return bookRepository.findByYearAndGenreName(genreName, year);
    }

    public List<Book> findByAuthorAndGenreName(String firstName, String lastName, String genreName) {
        return bookRepository.findByAuthorAndGenreName(firstName, lastName, genreName);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Collection<Book> saveBooks(Set<Book> books) {
        return bookRepository.save(books);
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }

    public void addBook(String[] bookName, String[] isbn, Integer[] year) {

        Set<Book> bookSet = new HashSet<>();

        for (int i = 0; i < bookName.length; i++) {
            Book book = new Book();
            book.setBookName(bookName[i]);
            book.setIsbn(isbn[i]);
            book.setYear(year[i]);
            LOG.info("Add book in Set: {}", ReflectionToString.reflectionToString(book));
            bookSet.add(book);
        }

        saveBooks(bookSet);
    }

    public Book editBook(FormBook formBook) {

        Book book = new Book();

        LOG.info("msg: book.setBookId({})", formBook.getBookId());
        book.setBookId(formBook.getBookId());

        LOG.info("msg: book.setBookName({})", formBook.getBookName());
        book.setBookName(formBook.getBookName());

        LOG.info("msg: book.setIsbn({})", formBook.getIsbn());
        book.setIsbn(formBook.getIsbn());

        LOG.info("msg: book.setYear({})", formBook.getYear());
        book.setYear(formBook.getYear());

        LOG.info("msg: book.setDescription({})", formBook.getDescription());
        book.setDescription(formBook.getDescription());

        if (formBook.getGenresNames() != null) {
            LOG.info("msg: book.setGenres(getGenreSet({}))", formBook.getGenresNames());

            Set<Genre> genreSet = new HashSet<>();

            for (String genreName: formBook.getGenresNames()) {

                if (genreName.compareTo("") != 0) {

                    LOG.info("msg: genreService.findByGenreName({}) ", genreName);
                    Genre genre = genreService.findByGenreName(genreName);

                    if (genre != null) {
                        LOG.info("msg: genreSet.add({})", genre);
                        genreSet.add(genre);
                    } else {
                        LOG.info("msg: genreSet.add(genreService.saveGenre(new Genre({})))", genreName);
                        genreSet.add(genreService.saveGenre(new Genre(genreName)));
                    }
                }
            }
            book.setGenres(genreSet);
        }

        if (formBook.getAuthorsFirstNames() != null || formBook.getAuthorsLastNames() != null) {

            Set<Author> authorSet = new HashSet<>();

            String[] authorsFirstNames = formBook.getAuthorsFirstNames();
            String[] authorsLastNames = formBook.getAuthorsLastNames();
            String[] authorsMiddleNames = formBook.getAuthorsMiddleNames();

            for (int i = 0; i < authorsFirstNames.length; i++) {

                if (authorsFirstNames[i].compareTo("") != 0) {

                    LOG.info("msg: authorService.findByFirstNameAndLastName({}, {}); ", authorsFirstNames[i], authorsLastNames[i]);
                    Author author = authorService.findByFirstNameAndLastName(authorsFirstNames[i], authorsLastNames[i]);

                    if (author != null) {
                        LOG.info("msg: authorSet.add({})", author);
                        authorSet.add(author);
                    } else {
                        LOG.info("msg: authorSet.add(authorService.saveAuthor(new Author({}, {}, {})))", authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]);
                        authorSet.add(authorService.saveAuthor(new Author(authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i])));
                    }
                }
            }

            LOG.info("msg: book.setAuthors(getAuthorSet({}, {}, {}))", formBook.getAuthorsFirstNames(), formBook.getAuthorsLastNames(), formBook.getAuthorsMiddleNames());
            book.setAuthors(authorSet);
        }

        if (formBook.getPublishersNames() != null) {

            Set<Publisher> publisherSet = new HashSet<>();

            for (String publisherName: formBook.getPublishersNames()) {

                if (publisherName.compareTo("") != 0) {

                    LOG.info("msg: publisherService.findByPublisherName({})", publisherName);
                    Publisher publisher = publisherService.findByPublisherName(publisherName);

                    if (publisher != null) {
                        LOG.info("msg: publisherSet.add({})", publisher);
                        publisherSet.add(publisher);
                    } else {
                        LOG.info("msg: publisherSet.add(publisherService.savePublisher(new Publisher({})))", publisherName);
                        publisherSet.add(publisherService.savePublisher(new Publisher(publisherName)));
                    }
                }
            }

            LOG.info("msg: book.setPublishers(getPublisherSet({}))", formBook.getPublishersNames());
            book.setPublishers(publisherSet);
        }

        return saveBook(book);
    }

    public ResponseEntity<StreamingResponseBody> exportBooks(List<Integer> id) throws IOException {

        List<Book> books = findBookByIterableId(id);

        final File csvFile = File.createTempFile("book_csv", null);

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
    }

    public List<PatternBook> searchBookInternet(String bookName) {

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
        return books;
    }

    public Book addSearchingBook(PatternBook patternBook) {

        String result = restTemplate.getForObject(patternBook.getHref(), String.class);

        Document document = Jsoup.parse(result);

        Elements bookSummarys = document.getElementsByClass("book-summary");

        for (Element bookSummary : bookSummarys) {
            Elements items = bookSummary.getElementsByClass("item");

            for (Element item : items) {
                if (item.text().contains("ISBN (EAN):")) {
                    patternBook.setIsbn(item.text().replace("ISBN (EAN):", "").trim());
                }
            }
        }

        Book book = new Book();

        book.setBookName(patternBook.getBookName());

        book.setIsbn(patternBook.getIsbn());

        if (patternBook.getYear() != null) {
            Integer year = Integer.parseInt(patternBook.getYear());
            book.setYear(year);
        }

        if (patternBook.getAuthorsNames() != null) {
            Set<Author> authorSet = new HashSet<>();
            for (String authorName : patternBook.getAuthorsNames().trim().split(",")) {
                String[] authorNameMassif = new String[3];
                int i = 0;
                for (String name : authorName.trim().split(" ")) {
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
                    author.setMiddleName(authorNameMassif[2]);
                    authorSet.add(authorService.saveAuthor(author));
                }
            }
            book.setAuthors(authorSet);
        }

        if (patternBook.getGenresNames() != null) {
            Set<Genre> genreSet = new HashSet<>();
            for (String genreName : patternBook.getGenresNames().split(",")) {
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
            for (String publisherName : patternBook.getPublishersNames().split(",")) {
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

        return saveBook(book);
    }
}
