package lab4.library.services;

import lab4.library.author.Author;
import lab4.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorServices {

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Set<Author> getAuthors(String authorNames) {

        Set<Author> authorSet = new HashSet<>();

        for (String authorName: authorNames.split(",")) {

            String[] authorNameMassif =   authorName.split(" ");

            String firstName = authorNameMassif[0].trim();
            String lastName = authorNameMassif[1].trim();
            String middleName = null;

            if (authorNameMassif.length == 3) {
                middleName = authorNameMassif[2];
            }

            Author author = findByFirstNameAndLastName(firstName, lastName);

            if (author != null) {
                authorSet.add(author);
            } else {
                authorSet.add(saveAuthor(new Author(firstName, lastName, middleName)));
            }
        }
        return authorSet;
    }

    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
