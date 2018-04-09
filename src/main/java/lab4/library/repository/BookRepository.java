package lab4.library.repository;

import lab4.library.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, QueryByExampleExecutor<Book> {

    List<Book> findByBookName(String bookName);

    Book findByIsbn(String isbn);

    List<Book> findByYear(int year);


    @Query("select b from Book b inner join b.genres g where g.genreName = :genreName and b.year = :year")
    List<Book> findByYearAndGenreName(@Param("genreName") String genreName, @Param("year") Integer year);

    @Query("select b from Book b join b.authors a join b.genres g where a.firstName = :firstName and a.lastName = :lastName and g.genreName = :genreName")
    List<Book> findByAuthorAndGenreName(@Param("firstName")String firstName, @Param("lastName") String lastName, @Param("genreName") String genreName);
}
