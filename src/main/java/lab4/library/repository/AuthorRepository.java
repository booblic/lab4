package lab4.library.repository;

import lab4.library.author.Author;
import lab4.library.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByFirstNameAndLastName(String firstName, String lastName);
}