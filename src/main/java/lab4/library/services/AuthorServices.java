package lab4.library.services;

import lab4.library.author.Author;
import lab4.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AuthorServices {

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        Example<Author> authorExample = Example.of(author);
        if (authorRepository.exists(authorExample)) {
            return authorRepository.findByFirstNameAndLastNameAndMiddleName(author.getFirstName(), author.getLastName(), author.getMiddleName());
        }
        return authorRepository.save(author);
    }
}
