package lab4.library.publisher;

import lab4.library.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer publisherId;

    @Column(unique = true)
    private String publisherName;

    @ManyToMany(mappedBy = "publishers")
    private Set<Book> books = new HashSet<>();

    Publisher() {}

    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
