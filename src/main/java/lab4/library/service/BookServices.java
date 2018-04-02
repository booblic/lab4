package lab4.library.service;

import lab4.library.ReflectionToString;
import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.book.PatternBook;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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

    public List<Book> findAllBookByCollectionId(Iterable<Integer> id) {
        return bookRepository.findAll(id);
    }

    public Collection<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Collection<Book> saveBooks(Set<Book> books) {
        return bookRepository.save(books);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book findOne(Integer id) {
        return bookRepository.findOne(id);
    }

    public List<Book> findByYearAndGenreName(String genreName, int year) {
        return bookRepository.findByYearAndGenreName(genreName, year);
    }

    public List<Book> findByAuthorAndGenreName(String firstName, String lastName, String genreName) {
        return bookRepository.findByAuthorAndGenreName(firstName, lastName, genreName);
    }

    public void addFindingBook(PatternBook patternBook) {

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

        saveBook(book);
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

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
