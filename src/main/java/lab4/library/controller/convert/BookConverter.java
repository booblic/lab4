package lab4.library.controller.convert;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.service.AuthorService;
import lab4.library.service.GenreService;
import lab4.library.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookConverter implements Converter<FormBook, Book> {

    private static final Logger LOG = LoggerFactory.getLogger(BookConverter.class);

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Override
    public Book convert(FormBook formBook) {

        Book book = new Book();

        LOG.info("msg: book.setBookId(formBook.getBookId());");
        book.setBookId(formBook.getBookId());

        LOG.info("msg: book.setBookName(formBook.getBookName());");
        book.setBookName(formBook.getBookName());

        LOG.info("msg: book.setIsbn(formBook.getIsbn());");
        book.setIsbn(formBook.getIsbn());

        LOG.info("msg: book.setYear(formBook.getYear());");
        book.setYear(formBook.getYear());

        LOG.info("msg: book.setDescription(formBook.getDescription());");
        book.setDescription(formBook.getDescription());

        if (formBook.getGenresNames() != null) {
            LOG.info("msg: if (formBook.getGenresNames() != null) { book.setGenres(getGenreSet(formBook.getGenresNames())); }");
            book.setGenres(getGenreSet(formBook.getGenresNames()));
        }

        if (formBook.getAuthorsFirstNames() != null || formBook.getAuthorsLastNames() != null) {
            LOG.info("msg: if (formBook.getAuthorsFirstNames() != null || formBook.getAuthorsLastNames() != null) { book.setAuthors(getAuthorSet(formBook.getAuthorsFirstNames(), formBook.getAuthorsLastNames(), formBook.getAuthorsMiddleNames())); }");
            book.setAuthors(getAuthorSet(formBook.getAuthorsFirstNames(), formBook.getAuthorsLastNames(), formBook.getAuthorsMiddleNames()));
        }

        if (formBook.getPublishersNames() != null) {
            LOG.info("msg: if (formBook.getPublishersNames() != null) { book.setPublishers(getPublisherSet(formBook.getPublishersNames())); }");
            book.setPublishers(getPublisherSet(formBook.getPublishersNames()));
        }

        return book;
    }

    private Set<Genre> getGenreSet(String[] genresNames) {

        Set<Genre> genreSet = new HashSet<>();

        for (String genreName: genresNames) {

            if (genreName.compareTo("") != 0) {

                LOG.info("msg: if (genreName.compareTo(\"\") != 0) { Genre genre = genreService.findByGenreName(genreName); " + genreName);
                Genre genre = genreService.findByGenreName(genreName);

                if (genre != null) {
                    LOG.info("msg: if (genre != null) { genreSet.add(genre); }");
                    genreSet.add(genre);
                } else {
                    LOG.info("msg: if (genre == null) { genreSet.add(genreService.saveGenre(new Genre(genreName))); }");
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

                LOG.info("msg: if (authorsFirstNames[i].compareTo(\"\") != 0) { Author author = authorService.findByFirstNameAndLastName(authorsFirstNames[i], authorsLastNames[i]); " + authorsFirstNames[i] + " " + authorsLastNames[i]);
                Author author = authorService.findByFirstNameAndLastName(authorsFirstNames[i], authorsLastNames[i]);

                if (author != null) {
                    LOG.info("msg: if (author != null) { authorSet.add(author); }");
                    authorSet.add(author);
                } else {
                    LOG.info("msg: if (author == null) { authorSet.add(authorService.saveAuthor(new Author(authorsFirstNames[i], authorsLastNames[i], authorsMiddleNames[i]))); }");
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

                LOG.info("msg: if (publisherName.compareTo(\"\") != 0) { Publisher publisher = publisherService.findByPublisherName(publisherName); " + publisherName);
                Publisher publisher = publisherService.findByPublisherName(publisherName);

                if (publisher != null) {
                    LOG.info("msg: if (publisher != null) { publisherSet.add(publisher); }");
                    publisherSet.add(publisher);
                } else {
                    LOG.info("msg: if (publisher == null) { publisherSet.add(publisherService.savePublisher(new Publisher(publisherName))); }");
                    publisherSet.add(publisherService.savePublisher(new Publisher(publisherName)));
                }
            }
        }
        return publisherSet;
    }
}
