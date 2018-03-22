package lab4.library.controller.convert;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.service.AuthorService;
import lab4.library.service.GenreService;
import lab4.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookConverter implements Converter<FormBook, Book> {

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Override
    public Book convert(FormBook formBook) {

        Book book = new Book();

        book.setBookId(formBook.getBookId());
        book.setBookName(formBook.getBookName());
        book.setIsbn(formBook.getIsbn());
        book.setYear(formBook.getYear());
        book.setDescription(formBook.getDescription());

        if (formBook.getGenresNames() != null) {
            book.setGenres(getGenreSet(formBook.getGenresNames()));
        }

        if (formBook.getAuthorsFirstNames() != null || formBook.getAuthorsLastNames() != null) {
            book.setAuthors(getAuthorSet(formBook.getAuthorsFirstNames(), formBook.getAuthorsLastNames(), formBook.getAuthorsMiddleNames()));
        }

        if (formBook.getPublishersNames() != null) {
            book.setPublishers(getPublisherSet(formBook.getPublishersNames()));
        }

        return book;
    }

    private Set<Genre> getGenreSet(String[] genresNames) {

        Set<Genre> genreSet = new HashSet<>();

        for (String genreName: genresNames) {

            if (genreName.compareTo("") != 0) {

                Genre genre = genreService.findByGenreName(genreName);

                if (genre != null) {
                    genreSet.add(genre);
                } else {
                    genreSet.add(genreService.saveGenre(new Genre(genreName)));
                }
            }
        }
        return genreSet;
    }

    private Set<Author> getAuthorSet(String[] authorsFirstNames, String[] authorsLastNames, String[] authorsMiddleNames) {

        Set<Author> authorSet = new HashSet<>();

        for (int i = 0; i < authorsFirstNames.length; i++) {

            if (authorsFirstNames[i].compareTo("") != 0) {

                Author author = authorService.findByFirstNameAndLastName(authorsFirstNames[i], authorsLastNames[i]);

                if (author != null) {
                    authorSet.add(author);
                } else {
                    authorSet.add(authorService.saveAuthor(new Author(authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i])));
                }
            }
        }
        return authorSet;
    }

    private Set<Publisher> getPublisherSet(String[] publishersNames) {

        Set<Publisher> publisherSet = new HashSet<>();

        for (String publisherName: publishersNames) {

            if (publisherName.compareTo("") != 0) {

                Publisher publisher = publisherService.findByPublisherName(publisherName);

                if (publisher != null) {
                    publisherSet.add(publisher);
                } else {
                    publisherSet.add(publisherService.savePublisher(new Publisher(publisherName)));
                }
            }
        }
        return publisherSet;
    }
}
