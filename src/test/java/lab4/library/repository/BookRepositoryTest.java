package lab4.library.repository;

import lab4.library.book.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VYZH
 * @since 23.03.2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(BookRepositoryTest.class);

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testFindQBE() throws Exception {
        Book b = new Book();
        b.setIsbn("978-5-699-08860-7");

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("bookRating", "year");
        Example<Book> example = Example.of(b, matcher);

        List<Book> books = bookRepository.findAll(example);
        LOG.info("found books = {}", books);

        Assert.assertTrue(books.size() > 0);
        Assert.assertTrue(books.stream().map(Book::getBookName).collect(Collectors.toSet()).contains("Война и мир"));
    }
}