package lab4.library.book;

import lab4.library.author.Author;
import lab4.library.publisher.Publisher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;

    private String name;

    private String isbn;

    private int year;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookGenre", joinColumns = @JoinColumn(name = "bookId"), inverseJoinColumns = @JoinColumn(name = "genreId"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookAuthor", joinColumns = @JoinColumn(name = "bookId"), inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookPublisher", joinColumns = @JoinColumn(name = "bookId"), inverseJoinColumns = @JoinColumn(name = "publisherId"))
    private Set<Publisher> publishers = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = new HashSet<>();

    private String periodicity;

    Book() {}

    public Book(String name, String isbn, int year, Set<Genre> genres, Set<Author> authors, Set<Publisher> publishers) {
        this.name = name;
        this.isbn = isbn;
        this.year = year;
        this.genres = genres;
        this.authors = authors;
        this.publishers = publishers;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
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

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
}
