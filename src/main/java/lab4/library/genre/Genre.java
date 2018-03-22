package lab4.library.genre;

import lab4.library.Description;
import lab4.library.annotation.ToString;
import lab4.library.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    Genre() {}

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
}
