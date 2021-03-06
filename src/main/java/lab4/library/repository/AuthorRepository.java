package lab4.library.repository;

import lab4.library.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);

    Author findByFirstNameAndLastName(String firstName, String lastName);

    Author findByLastName(String lastName);
}