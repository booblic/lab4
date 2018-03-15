package lab4.library.repository;

import lab4.library.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBookName(String bookName);


    List<Book> findByBookNameAAndYear(String bookName, Integer year);
}
