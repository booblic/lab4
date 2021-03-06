package lab4.library.book;

import lab4.library.ReflectionToString;
import lab4.library.annotation.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * The class whose object is generated when the user receives a completed form
 * @author Кирилл
 * @version 1.0
 */
public class FormBook {

    @ToString
    private Integer bookId;

    @ToString
    private String bookName;

    @ToString
    private String isbn;

    @ToString
    private Integer year;

    /**
     * An array with genre names separated by a comma
     */
    private String[] genresNames;

    /**
     * An array with the names of authors separated by a comma
     */
    private String[] authorsFirstNames;

    /**
     * An array with author last names separated by a comma
     */
    private String[] authorsLastNames;

    /**
     * Array with the authors' middle names, separated by commas
     */
    private String[] authorsMiddleNames;

    /**
     * An array with the names of publishers separated by a comma
     */
    private String[] publishersNames;

    @ToString
    private String description;

    @ToString
    private Integer viewsNumber;

    @ToString
    private String summaryPath;

    @ToString
    private int summaryNumberPage;

    @ToString
    private MultipartFile file;

    @ToString
    private boolean free;

    public FormBook() {}

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String[] getGenresNames() {
        return genresNames;
    }

    public void setGenresNames(String[] genresNames) {
        this.genresNames = genresNames;
    }

    public String[] getAuthorsFirstNames() {
        return authorsFirstNames;
    }

    public void setAuthorsFirstNames(String[] authorsFirstNames) {
        this.authorsFirstNames = authorsFirstNames;
    }

    public String[] getAuthorsLastNames() {
        return authorsLastNames;
    }

    public void setAuthorsLastNames(String[] authorsLastNames) {
        this.authorsLastNames = authorsLastNames;
    }

    public String[] getAuthorsMiddleNames() {
        return authorsMiddleNames;
    }

    public void setAuthorsMiddleNames(String[] authorsMiddleNames) {
        this.authorsMiddleNames = authorsMiddleNames;
    }

    public String[] getPublishersNames() {
        return publishersNames;
    }

    public void setPublishersNames(String[] publishersNames) {
        this.publishersNames = publishersNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViewsNumber() {
        return viewsNumber;
    }

    public void setViewsNumber(Integer viewsNumber) {
        this.viewsNumber = viewsNumber;
    }

    public String getSummaryPath() {
        return summaryPath;
    }

    public void setSummaryPath(String summaryPath) {
        this.summaryPath = summaryPath;
    }

    public int getSummaryNumberPage() {
        return summaryNumberPage;
    }

    public void setSummaryNumberPage(int summaryNumberPage) {
        this.summaryNumberPage = summaryNumberPage;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}
