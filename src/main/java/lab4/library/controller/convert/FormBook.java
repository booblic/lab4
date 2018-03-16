package lab4.library.controller.convert;

public class FormBook {

    private Integer bookId;

    private String bookName;

    private String isbn;

    private int year;

    private String[] genresNames;

    private String[] authorsFirstNames;

    private String[] authorsLastNames;

    private String[] authorsMiddleNames;

    private String[] publishersNames;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
}
