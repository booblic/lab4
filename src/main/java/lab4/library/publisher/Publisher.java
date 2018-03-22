package lab4.library.publisher;

import lab4.library.Description;
import lab4.library.annotation.ToString;
import lab4.library.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Publisher extends Description {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer publisherId;

    @Column(unique = true)
    @ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher)) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(publisherId, publisher.publisherId) &&
                Objects.equals(publisherName, publisher.publisherName) &&
                Objects.equals(books, publisher.books);
    }

    @Override
    public int hashCode() {

        return Objects.hash(publisherId, publisherName);
    }
}
