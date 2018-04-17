package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.book.FormBook;
import lab4.library.book.Printing;

import java.util.List;
import java.util.Set;

public abstract class BookService {

    abstract Book findOne(Integer id);

    abstract List<Book> findAllBook();

    abstract Book saveBook(Book book);

    abstract Printing addOrEditBook(FormBook formBook);

    abstract void deleteBook(Integer id);
}
