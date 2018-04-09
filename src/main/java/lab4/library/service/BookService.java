package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.book.FormBook;

import java.util.List;
import java.util.Set;

public interface BookService {

    Book findOne(Integer id);

    List<Book> findAllBook();

    Book saveBook(Book book);

    Book addOrEditBook(FormBook formBook);

    void deleteBook(Integer id);
}
