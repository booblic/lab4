package lab4.library.book;

import org.springframework.web.bind.annotation.PathVariable;

public class PatternBook {

    private Integer count;

    private String bookName;

    private String authorsNames;

    private String year;

    private String genresNames;

    private String publishersNames;

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
}
