package lab4.library.service;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    public Collection<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Collection<Book> saveBook(String[] bookName, String[] isbn, Integer[] year) {
        Set<Book> bookSet = new HashSet<>();
        for (int i = 0; i < bookName.length; i++) {
            Book book = new Book();
            book.setBookName(bookName[i]);
            book.setIsbn(isbn[i]);
            book.setYear(year[i]);
            bookSet.add(book);
        }
        return bookRepository.save(bookSet);
    }

    public Book editBook(Integer id, Book book) {
        return book; // save book & return updated instance from repository
    }

    public Book editBook(Book book, String[] genreNames, String[] firstNames, String[] lastNames, String[] middleNames, String[] publisherNames) {

        Set<Genre> genreSet;
        Set<Author> authorSet;
        Set<Publisher> publisherSet;

        if (genreNames.length != 0) {
            genreSet = genreService.findOrCreateGenres(genreNames);
            book.setGenres(genreSet);
        }

        if (firstNames.length != 0) {
            authorSet = authorService.findOrCreateAuthors(firstNames, lastNames, middleNames);
            book.setAuthors(authorSet);
        }

        if (publisherNames.length != 0) {
            publisherSet = publisherService.findOrCreatePublisher(publisherNames);
            book.setPublishers(publisherSet);
        }
        return bookRepository.save(book);
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book findBook(Integer id) {
        return bookRepository.findOne(id);
    }
}
