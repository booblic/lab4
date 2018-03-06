package lab4.library.service;

import lab4.library.author.Author;
import lab4.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Set<Author> getAuthors(String[] firstNames, String[] lastNames, String[] middleNames) {

        Set<Author> authorSet = new HashSet<>();

        for (int i = 0; i < firstNames.length; i++) {

            if (firstNames[i].compareTo("") != 0) {

                Author author = findByFirstNameAndLastName(firstNames[i], lastNames[i]);

                if (author != null) {
                    authorSet.add(author);
                } else {
                    authorSet.add(saveAuthor(new Author(firstNames[i], lastNames[i], middleNames[i])));
                }
            }
        }
        return authorSet;
    }

    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
