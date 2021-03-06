package lab4.library.service;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.book.FormBook;
import lab4.library.exception.ResourceNotFoundException;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.*;

/**
 * A service that implements business logic for a book
 *
 * @author Кирилл
 * @version 1.0
 */
@Service
public class BookServiceImpl extends BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private PublisherServiceImpl publisherService;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Book findOne(Integer id) {
        return bookRepository.findOne(id);
    }

    @Transactional
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName.toLowerCase());
    }

    @Transactional
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Transactional
    public List<Book> findBookByIterableId(Iterable<Integer> id) {
        return bookRepository.findAll(id);
    }

    @Transactional
    public List<Book> findByYearAndGenreName(String genreName, Integer year) {
        return bookRepository.findByYearAndGenreName(genreName, year);
    }

    @Transactional
    public List<Book> findByAuthorAndGenreName(String firstName, String lastName, String genreName) {
        return bookRepository.findByAuthorAndGenreName(firstName, lastName, genreName);
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }

    /**
     * A method based on information from formBook forms a book object and stores / updates it in the database
     *
     * @param formBook - book information object
     * @return object Book
     */
    @Transactional
    public Book addOrEditBook(FormBook formBook) {

        Book book = new Book();

        LOG.info("msg: book.setBookId({})", formBook.getBookId());
        book.setBookId(formBook.getBookId());

        LOG.info("msg: book.setViewsNumber({})", formBook.getViewsNumber());
        book.setViewsNumber(formBook.getViewsNumber());

        LOG.info("msg: book.setSummaryNumberPage({})", formBook.getSummaryPath());
        book.setSummaryNumberPage(formBook.getSummaryNumberPage());

        LOG.info("msg: book.setSummaryPath({})", formBook.getSummaryPath());
        book.setSummaryPath(formBook.getSummaryPath());

        LOG.info("msg: book.setBookName({})", formBook.getBookName());
        book.setBookName(formBook.getBookName());

        LOG.info("msg: book.setIsbn({})", formBook.getIsbn());
        book.setIsbn(formBook.getIsbn());

        LOG.info("msg: book.setYear({})", formBook.getYear());
        book.setYear(formBook.getYear());

        LOG.info("msg: book.setDescription({})", formBook.getDescription());
        book.setDescription(formBook.getDescription());

        LOG.info("msg: book.setSummaryPath({})", formBook.getFree());
        book.setFree(formBook.getFree());

        LOG.info("msg: formBook.getFile({})", formBook.getFile());
        MultipartFile multipartFile = formBook.getFile();
        if (multipartFile.getOriginalFilename() != "") {
            try {
                String fileSeparator = System.getProperty("file.separator");
                File summaryFile = new File("summary" + fileSeparator + multipartFile.getOriginalFilename());
                summaryFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(summaryFile);
                fos.write(multipartFile.getBytes());
                fos.close();
                book.setSummaryPath(summaryFile.getPath());

                FileInputStream fis = new FileInputStream(book.getSummaryPath());
                XWPFDocument document = new XWPFDocument(fis);
                book.setSummaryNumberPage(document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages());
            } catch (IOException e) {
                LOG.error("Error creating file", e);
            }
        }

        if (formBook.getGenresNames() != null) {

            Set<Genre> genreSet = new HashSet<>();

            for (String genreName : formBook.getGenresNames()) {

                if (genreName.compareTo("") != 0) {

                    LOG.info("msg: genreService.findByGenreName({}) ", genreName);
                    Genre genre = genreService.findByGenreName(genreName);

                    if (genre != null) {

                        LOG.info("msg: genreSet.add({})", genre);
                        genreSet.add(genre);

                    } else {

                        LOG.info("msg: genreSet.add(genreService.saveGenre(new Genre({})))", genreName);
                        genreSet.add(genreService.save(new Genre(genreName)));
                    }
                }
            }
            LOG.info("msg: book.setGenres(genreSet)");
            book.setGenres(genreSet);
        }

        if (formBook.getAuthorsFirstNames().length != 0 || formBook.getAuthorsLastNames().length != 0 || formBook.getAuthorsMiddleNames().length != 0) {

            Set<Author> authorSet = new HashSet<>();


            LOG.info("msg: formBook.getAuthorsFirstNames()");
            String[] authorsFirstNames = formBook.getAuthorsFirstNames();

            LOG.info("msg: formBook.getAuthorsLastNames()");
            String[] authorsLastNames = formBook.getAuthorsLastNames();

            LOG.info("msg: formBook.getAuthorsMiddleNames()");
            String[] authorsMiddleNames = formBook.getAuthorsMiddleNames();

            if (authorsFirstNames.length != 0) {

                for (int i = 0; i < authorsFirstNames.length; i++) {

                    if (authorsFirstNames[i].compareTo("") != 0) {

                        Author author = null;

                        if (authorsLastNames.length != 0 && authorsMiddleNames.length != 0) {

                            LOG.info("msg: authorService.findByFirstNameAndLastNameAndMiddleName({}, {}, {}); ", authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]);
                            author = authorService.findByFirstNameAndLastNameAndMiddleName(authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]);

                        } else if (authorsLastNames.length != 0) {
                            LOG.info("msg: authorService.findByFirstNameAndLastName({}, {}); ", authorsFirstNames[i], authorsLastNames[i]);
                            author = authorService.findByFirstNameAndLastName(authorsFirstNames[i], authorsLastNames[i]);
                        }

                        if (author != null) {

                            LOG.info("msg: authorSet.add({})", author);
                            authorSet.add(author);

                        } else {

                            if (authorsMiddleNames.length != 0) {
                                LOG.info("msg: authorSet.add(authorService.saveAuthor(new Author({}, {}, {})))", authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]);
                                authorSet.add(authorService.save(new Author(authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i])));
                            } else {
                                LOG.info("msg: authorSet.add(authorService.saveAuthor(new Author({}, {}, {})))", authorsFirstNames[i], authorsLastNames[i]);
                                authorSet.add(authorService.save(new Author(authorsFirstNames[i], authorsLastNames[i], "")));
                            }
                        }
                    } else if (authorsLastNames[i].compareTo("") != 0) {

                        LOG.info("msg: authorService.findByLastName({}); ", authorsLastNames[i]);
                        Author author = authorService.findByFirstNameAndLastNameAndMiddleName("", authorsLastNames[i], "");

                        if (author != null) {

                            LOG.info("msg: authorSet.add({})", author);
                            authorSet.add(author);

                        } else {

                            LOG.info("msg: authorSet.add(authorService.saveAuthor(new Author({}, {}, {})))", authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]);
                            authorSet.add(authorService.save(new Author("", authorsLastNames[i], "")));
                        }
                    }
                }
            } else if (authorsLastNames.length != 0) {

                for (int i = 0; i < authorsLastNames.length; i++) {

                    LOG.info("msg: authorService.findByLastName({}); ", authorsLastNames[i]);
                    Author author = authorService.findByFirstNameAndLastNameAndMiddleName("", authorsLastNames[i], "");

                    if (author != null) {

                        LOG.info("msg: authorSet.add({})", author);
                        authorSet.add(author);

                    } else {

                        LOG.info("msg: authorSet.add(authorService.saveAuthor(new Author({})))", authorsLastNames[i]);
                        authorSet.add(authorService.save(new Author("", authorsLastNames[i], "")));
                    }
                }
            }

            LOG.info("msg: book.setAuthors(authorSet)");
            book.setAuthors(authorSet);
        }

        if (formBook.getPublishersNames() != null) {

            Set<Publisher> publisherSet = new HashSet<>();

            for (String publisherName : formBook.getPublishersNames()) {

                if (publisherName.compareTo("") != 0) {

                    LOG.info("msg: publisherService.findByPublisherName({})", publisherName);
                    Publisher publisher = publisherService.findByPublisherName(publisherName);

                    if (publisher != null) {

                        LOG.info("msg: publisherSet.add({})", publisher);
                        publisherSet.add(publisher);

                    } else {

                        LOG.info("msg: publisherSet.add(publisherService.savePublisher(new Publisher({})))", publisherName);
                        publisherSet.add(publisherService.save(new Publisher(publisherName)));
                    }
                }
            }
            LOG.info("msg: book.setPublishers()");
            book.setPublishers(publisherSet);
        }
        LOG.info("return saveBook({})", book.toString());
        return saveBook(book);
    }

    /**
     * The method uses a repository to retrieve books, generates a file with information about them
     *
     * @param id - list of id books
     * @return file with information about books
     * @throws IOException
     */
    @Transactional
    public ResponseEntity<StreamingResponseBody> exportBooks(List<Integer> id) throws IOException {

        LOG.info("msg: findBookByIterableId({})", id);
        List<Book> books = findBookByIterableId(id);

        LOG.info("msg: File.createTempFile(\"book_csv\", null)");
        final File csvFile = File.createTempFile("book_csv", null);

        LOG.info("msg: System.getProperty(\"line.separator\")");
        String lineSeparator = System.getProperty("line.separator");

        LOG.info("msg: new FileWriter({})", csvFile);
        try (Writer writer = new FileWriter(csvFile)) {

            for (Book book : books) {

                LOG.info("msg:  writer.append({})", book.getBookName());
                LOG.info("msg:  writer.append({})", book.getIsbn());
                LOG.info("msg:  writer.append({})", String.valueOf(book.getYear()));
                writer.append(book.getBookName())
                        .append(",")
                        .append(book.getIsbn())
                        .append(",")
                        .append(String.valueOf(book.getYear()))
                        .append(lineSeparator);
            }

        } catch (IOException exception) {
            LOG.error("msg: printStackTrace()", exception);
        }
        LOG.info("msg: header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=books.csv\")");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv").body((OutputStream outputStream) -> {

            LOG.info("msg: StreamUtils.copy(new BufferedInputStream(new FileInputStream(csvFile)), outputStream\n");
            StreamUtils.copy(
                    new BufferedInputStream(new FileInputStream(csvFile)),
                    outputStream
            );
            LOG.info("msg: csvFile.delete()");
            csvFile.delete();
        });
    }

    /**
     * The method makes an HTTP request, gets an html page containing information about books and on its basis forms a list of books
     *
     * @param bookName - name of the book
     * @return list of PatternBook objects
     */
    @Transactional
    public List<PatternBook> searchBookInternet(String bookName) {

        List<PatternBook> books = new ArrayList<>();

        Map<String, String> vars = new HashMap<>();

        LOG.info("msg: vars.put(\"name\", {})", bookName);
        vars.put("name", bookName);

        LOG.info("msg: restTemplate.getForObject(\"https://mybook.ru/search/books/?q={name}\", {}, {})", String.class, vars);
        String result = restTemplate.getForObject("https://mybook.ru/search/books/?q={name}", String.class, vars);

        LOG.info("msg: Jsoup.parse(result)");
        Document document = Jsoup.parse(result);

        LOG.info("msg: document.getElementsByClass(\"search-result__item-info\")");
        Elements searchResultItemInfos = document.getElementsByClass("search-result__item-info");

        LOG.info("count = 1");
        int count = 1;

        for (Element searchResultItemInfo : searchResultItemInfos) {

            PatternBook book = new PatternBook();

            LOG.info("msg: searchResultItemInfo.getElementsByClass(\"search-result__item-info_author\");");
            Elements searchResultItemInfoAuthors = searchResultItemInfo.getElementsByClass("search-result__item-info_author");

            LOG.info("searchResultItemInfo.getElementsByClass(\"search-result__item_more\");");
            Elements searchResultItemMores = searchResultItemInfo.getElementsByClass("search-result__item_more");

            for (Element searchResultItemInfoAuthor : searchResultItemInfoAuthors) {

                LOG.info("book.setBookName({})", searchResultItemInfoAuthor.text());
                book.setBookName(searchResultItemInfoAuthor.text());

                LOG.info("book.setHref(\"https://mybook.ru/\" + {})", searchResultItemInfoAuthor.attr("href"));
                book.setHref("https://mybook.ru/" + searchResultItemInfoAuthor.attr("href"));

                LOG.info("msg: book.setCount({})", count);
                book.setCount(count++);
            }

            for (Element searchResultItemMore : searchResultItemMores) {

                LOG.info("msg: searchResultItemMore.getElementsByTag(\"p\");");
                Elements elementsTagPs = searchResultItemMore.getElementsByTag("p");

                for (Element elementsTagP : elementsTagPs) {

                    LOG.info("mag: String info = {}", elementsTagP.text());
                    String info = elementsTagP.text();

                    if (info.toLowerCase().contains("Год издания:".toLowerCase())) {

                        LOG.info("msg: book.setYear({})", info.replace("Год издания:", "").trim());
                        book.setYear(info.replace("Год издания:", "").trim());
                    }

                    if (info.toLowerCase().contains("Правообладатель:".toLowerCase())) {

                        LOG.info("msg: book.setPublishersNames({})", info.replace("Правообладатель:", "").trim());
                        book.setPublishersNames(info.replace("Правообладатель:", "").trim());
                    }

                    if (info.toLowerCase().contains("Жанр:".toLowerCase())) {

                        LOG.info("msg: book.setGenresNames({})", info.replace("Жанр:", "").trim());
                        book.setGenresNames(info.replace("Жанр:", "").trim());
                    }

                    LOG.info("msg: elementsTagP.getElementsByTag(\"a\");");
                    Elements elementsTagAs = elementsTagP.getElementsByTag("a");

                    for (Element elementsTagA : elementsTagAs) {

                        LOG.info("msg: elementsTagA.getElementsByAttributeValueMatching(\"href\", \"/author/\");");
                        Elements authorsNames = elementsTagA.getElementsByAttributeValueMatching("href", "/author/");

                        for (Element authorName : authorsNames) {

                            if (book.getAuthorsNames() != null) {

                                LOG.info("msg: book.setAuthorsNames({} + \", \" + {})", book.getAuthorsNames(), authorName.text());
                                book.setAuthorsNames(book.getAuthorsNames() + ", " + authorName.text());

                            } else {

                                LOG.info("msg: book.setAuthorsNames({})", authorsNames.text());
                                book.setAuthorsNames(authorsNames.text());
                            }
                        }
                    }
                }
            }
            LOG.info("msg: books.add({})", book.toString());
            books.add(book);
        }
        return books;
    }

    /**
     * The method makes an HTTP request, receives an HTML page, extracts the ISBN of the book from it, based on the information contained in the PatternBook object it forms a Book object and stores it in the database
     *
     * @param patternBook - book information object
     * @return object Book
     */
    @Transactional
    public Book addSearchingBook(PatternBook patternBook) {

        LOG.info("msg: restTemplate.getForObject({}, {})", patternBook.getHref(), String.class);
        String result = restTemplate.getForObject(patternBook.getHref(), String.class);

        LOG.info("msg: Jsoup.parse(result)");
        Document document = Jsoup.parse(result);

        LOG.info("msg: document.getElementsByClass(\"book-summary\")");
        Elements bookSummarys = document.getElementsByClass("book-summary");

        for (Element bookSummary : bookSummarys) {

            LOG.info("msg: bookSummary.getElementsByClass(\"item\")");
            Elements items = bookSummary.getElementsByClass("item");

            for (Element item : items) {

                if (item.text().contains("ISBN (EAN):")) {

                    LOG.info("msg: patternBook.setIsbn({})", item.text().replace("ISBN (EAN):", "").trim());
                    patternBook.setIsbn(item.text().replace("ISBN (EAN):", "").trim());
                }
            }
        }

        Book book = new Book();

        LOG.info("msg: book.setBookName({})", patternBook.getBookName());
        book.setBookName(patternBook.getBookName());

        LOG.info("msg: book.setIsbn({})", patternBook.getIsbn());
        book.setIsbn(patternBook.getIsbn());

        if (patternBook.getYear() != null) {

            LOG.info("msg: Integer.parseInt({})", patternBook.getYear());
            Integer year = Integer.parseInt(patternBook.getYear());
            book.setYear(year);
        }

        if (patternBook.getAuthorsNames() != null) {

            Set<Author> authorSet = new HashSet<>();

            for (String authorName : patternBook.getAuthorsNames().trim().split(",")) {

                String[] authorNameMassif = new String[3];

                LOG.info("msg: i = 0");
                int i = 0;

                for (String name : authorName.trim().split(" ")) {

                    LOG.info("msg: authorNameMassif[{}] = {}", i, name);
                    authorNameMassif[i] = name;

                    LOG.info("msg: i = {}", i);
                    i++;
                }

                LOG.info("msg: authorService.findByFirstNameAndLastName({}, {})", authorNameMassif[0], authorNameMassif[1]);
                Author existingAuthor = authorService.findByFirstNameAndLastName(authorNameMassif[0], authorNameMassif[1]);

                if (existingAuthor != null) {

                    LOG.info("msg: authorSet.add({})", existingAuthor.toString());
                    authorSet.add(existingAuthor);

                } else {

                    Author author = new Author();

                    LOG.info("msg: author.setFirstName({})", authorNameMassif[0]);
                    author.setFirstName(authorNameMassif[0]);

                    LOG.info("msg: author.setLastName({})", authorNameMassif[1]);
                    author.setLastName(authorNameMassif[1]);

                    LOG.info("msg: author.setMiddleName({})", authorNameMassif[2]);
                    author.setMiddleName(authorNameMassif[2]);

                    LOG.info("msg: authorSet.add(authorService.saveAuthor({}));", author.toString());
                    authorSet.add(authorService.save(author));
                }
            }

            LOG.info("msg: book.setAuthors(authorSet)");
            book.setAuthors(authorSet);
        }

        if (patternBook.getGenresNames() != null) {

            Set<Genre> genreSet = new HashSet<>();

            for (String genreName : patternBook.getGenresNames().split(",")) {

                LOG.info("msg: genreService.findByGenreName({})", genreName.trim());
                Genre existingGenre = genreService.findByGenreName(genreName.trim());

                if (existingGenre != null) {

                    LOG.info("msg: genreSet.add({})", existingGenre.toString());
                    genreSet.add(existingGenre);

                } else {

                    Genre genre = new Genre();

                    LOG.info("msg: genre.setGenreName({})", genreName.trim());
                    genre.setGenreName(genreName.trim());

                    LOG.info("msg: genreSet.add(genreService.saveGenre({}))", genre.toString());
                    genreSet.add(genreService.save(genre));
                }
            }
            LOG.info("msg: book.setGenres(genreSet)");
            book.setGenres(genreSet);
        }

        if (patternBook.getPublishersNames() != null) {

            Set<Publisher> publisherSet = new HashSet<>();

            for (String publisherName : patternBook.getPublishersNames().split(",")) {

                LOG.info("msg: publisherService.findByPublisherName({})", publisherName.trim());
                Publisher existingPublisher = publisherService.findByPublisherName(publisherName.trim());

                if (existingPublisher != null) {

                    LOG.info("msg: publisherSet.add({})", existingPublisher.toString());
                    publisherSet.add(existingPublisher);

                } else {

                    Publisher publisher = new Publisher();

                    LOG.info("msg: publisher.setPublisherName({})", publisherName.trim());
                    publisher.setPublisherName(publisherName.trim());

                    LOG.info("msg: publisherSet.add(publisherService.savePublisher({}))", publisher.toString());
                    publisherSet.add(publisherService.save(publisher));
                }
            }
            LOG.info("msg: book.setPublishers(publisherSet)");
            book.setPublishers(publisherSet);
        }
        return saveBook(book);
    }

    @Transactional
    public String getBookSummary(Integer bookId) {
        Book book = findOne(bookId);
        StringBuilder summary = new StringBuilder();
        if (book.getSummaryPath() != null && book.getSummaryPath() != "") {
            try {
                FileInputStream fis = new FileInputStream(book.getSummaryPath());
                XWPFDocument document = new XWPFDocument(fis);
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph para : paragraphs) {
                    summary.append(para.getText()).append("\n\n");
                }
                fis.close();
            } catch (Exception e) {
                LOG.error("No file", e);
            }
        } else {
            summary.append("Краткое содеражние скоро появится");
        }
        return summary.toString();
    }

    @Transactional
    public ResponseEntity<StreamingResponseBody> download(Integer bookId) throws IOException {
        LOG.info("msg: findOne({})", bookId);
        Book book = findOne(bookId);
        if (book.getSummaryPath() == null) {
            throw new ResourceNotFoundException();
        }

        final File summary = new File(book.getSummaryPath());
        String canonicalSummaryName = summary.getName().replaceAll("\\s+", "_");

        LOG.info("msg: header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\"+canonicalSummaryName+\".docx\")");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + canonicalSummaryName).body((OutputStream outputStream) -> {

            LOG.info("msg: StreamUtils.copy(new BufferedInputStream(new FileInputStream(summary)), outputStream\n");
            StreamUtils.copy(new BufferedInputStream(new FileInputStream(summary)), outputStream);

            LOG.info("msg: csvFile.delete()");
            summary.delete();
        });
    }

    @Transactional
    public long getBookCount() {
        return bookRepository.count();
    }

    public List<Book> findByAuthor(String data) {
        List<Book> booksByAuthor = new ArrayList<>();
        List<String> authorName = Arrays.asList(data.toLowerCase().split(" "));
        if (authorName.size() == 3) {
            booksByAuthor = bookRepository.findByAuthorFullName(authorName.get(0), authorName.get(1), authorName.get(2));
        } else if (authorName.size() == 2) {
            booksByAuthor = bookRepository.findByAuthor(authorName.get(0), authorName.get(1));
        } else if (authorName.size() == 1) {
            booksByAuthor = bookRepository.findByAuthorLastName(authorName.get(0));
        } else if (authorName.size() > 3) {
            booksByAuthor = bookRepository.findByAuthorFullName(authorName.get(0), authorName.get(1), authorName.get(2));
        }
        return booksByAuthor;
    }
}
