package lab4.library.book;

import java.util.List;

public class Book {

    private int bookId;

    private String name;

    private String isbn;

    private int year;

    //private int authorId;

    //private int publisherId;

    //private List<Author> authors;

    //private List<Publisher> publishers;

    Book() {}

    Book(int bookId, String name, String isbn, int year) {
        this.bookId = bookId;
        this.name = name;
        this.isbn = isbn;
        this.year = year;
    }

    /*Book(int bookId, String name, int authorId, int pubkisherId, String isbn, int year) {
        this.bookId = bookId;
        this.name = name;
        this.authorId = authorId;
        this.publisherId = pubkisherId;
        this.isbn = isbn;
        this.year = year;
    }*/

    /*Book(String name, List<Author> authors, List<Publisher> publishers, long isbn, int year) {
        this.name = name;
        this.authors = authors;
        this.publishers = publishers;
        this.isbn = isbn;
        this.year = year;
    }*/

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    /*public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }*/

    /*public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }*/
}
