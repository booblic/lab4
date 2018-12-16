package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findTopViews() {
        return bookRepository.findTop10ByOrderByViewsNumberDesc();
    }
}
