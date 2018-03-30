package lab4.library.repository;

import lab4.library.book.Book;
import lab4.library.genre.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VYZH
 * @since 25.03.2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Test
    public void testFindAllByGenres() throws Exception {
        Genre genre = genreRepository.findOne(4);

        Book book = new Book();
        book.setGenres(Collections.singleton(genre));

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("genres.id", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.EXACT))
                .withIgnorePaths("bookRating", "year");
        Example<Book> example = Example.of(book, matcher);

        List<Book> booksFromRepository = bookRepository.findAll(example);
        List<String> names = booksFromRepository.stream().map(Book::getBookName).collect(Collectors.toList());

        Assert.assertNotNull(booksFromRepository);
        Assert.assertTrue(names.contains("Война и мир"));
        Assert.assertTrue(names.contains("Преступление и наказание"));
    }
}