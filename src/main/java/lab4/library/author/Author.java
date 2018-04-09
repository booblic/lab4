package lab4.library.author;

import lab4.library.Description;
import lab4.library.ReflectionToString;
import lab4.library.annotation.ToString;
import lab4.library.book.Book;
import sun.security.krb5.internal.crypto.Des;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс представляющий сущность - автор
 * @author Кирилл
 * @version 1.0
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName", "middleName"})})
public class Author extends Description {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authorId;

    @ToString
    private String firstName;

    @ToString
    private String lastName;

    private String middleName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author() {}

    public Author(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(middleName, author.middleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(authorId, firstName, lastName, middleName);
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}
