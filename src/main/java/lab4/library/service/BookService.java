package lab4.library.service;

import lab4.library.book.Book;

import java.util.List;
import java.util.Set;

public interface BookService {

    Book findOne(Integer id);

    List<Book> findAllBook();

    Book saveBook(Book book);

    List<Book> saveBooks(Set<Book> books);

    void deleteBook(Integer id);
}
