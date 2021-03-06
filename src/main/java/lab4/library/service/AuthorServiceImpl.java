package lab4.library.service;

import lab4.library.author.Author;
import lab4.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service that implements business logic for author
 * @author Кирилл
 * @version 1.0
 */
@Service
public class AuthorServiceImpl implements EntityService<Author> {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author findByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName) {
        return authorRepository.findByFirstNameAndLastNameAndMiddleName(firstName, lastName, middleName);
    }

    @Transactional
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    public Author findByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }
}
