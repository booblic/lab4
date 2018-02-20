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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genreId")
    private Genre genre;

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

    public Book(String name, String isbn, int year, Genre genre, Set<Author> authors, Set<Publisher> publishers) {
        this.name = name;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
