package lab4.library.services;

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
    private GenreServices genreServices;

    @Autowired
    private AuthorServices authorServices;

    @Autowired
    private PublisherServices publisherServices;

    public Collection<Book> findAllBook() {
        /*List<Book> list = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            list.add(book);
        }
        return list;*/
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book editBook(Book book, String genreNames, String authorNames, String publisherNames) {

        Set<Genre> genreSet;
        Set<Author> authorSet;
        Set<Publisher> publisherSet;

        if (genreNames != null) {
            genreSet = genreServices.getGenres(genreNames);
            book.setGenres(genreSet);
        }

        if (authorNames != null) {
            authorSet = authorServices.getAuthors(authorNames);
            book.setAuthors(authorSet);
        }

        if (publisherNames != null) {
            publisherSet = publisherServices.getPublisher(publisherNames);
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
