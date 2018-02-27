package lab4.library.services;

import lab4.library.book.Book;
import lab4.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBook() {
        List<Book> list = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            list.add(book);
        }
        return list;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book findBook(Integer id) {
        return bookRepository.findOne(id);
    }
}
