package lab4.library.genre;

import lab4.library.Description;
import lab4.library.ReflectionToString;
import lab4.library.annotation.ToString;
import lab4.library.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing entity - genre
 * @author Кирилл
 * @version 1.0
 */
@Entity
public class Genre extends Description {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer genreId;

    @Column(unique = true)
    @ToString
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    public Genre() {}

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
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
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(genreId, genre.genreId) &&
                Objects.equals(genreName, genre.genreName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(genreId, genreName);
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}
