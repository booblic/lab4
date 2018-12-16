package lab4.library.service;

import com.itextpdf.text.DocumentException;
import lab4.library.book.Book;
import lab4.library.book.FormBook;

import java.util.List;
import java.util.Set;

public abstract class BookService {

    abstract Book findOne(Integer id);

    abstract List<Book> findAllBook();

    abstract Book saveBook(Book book);

    abstract Book addOrEditBook(FormBook formBook) throws DocumentException;

    abstract void deleteBook(Integer id);
}
