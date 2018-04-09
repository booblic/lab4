package lab4.library.book;

import lab4.library.annotation.ToString;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Class for working with data received from the Internet (import of information about the book)
 * @author Кирилл
 * @version 1.0
 */
public class PatternBook {

    @ToString
    private Integer count;

    @ToString
    private String bookName;

    @ToString
    private String isbn;

    @ToString
    private String authorsNames;

    @ToString
    private String year;

    @ToString
    private String genresNames;

    @ToString
    private String publishersNames;

    @ToString
    private String href;

    public PatternBook() {}

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorsNames() {
        return authorsNames;
    }

    public void setAuthorsNames(String authorsNames) {
        this.authorsNames = authorsNames;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenresNames() {
        return genresNames;
    }

    public void setGenresNames(String genresNames) {
        this.genresNames = genresNames;
    }

    public String getPublishersNames() {
        return publishersNames;
    }

    public void setPublishersNames(String publishersNames) {
        this.publishersNames = publishersNames;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
