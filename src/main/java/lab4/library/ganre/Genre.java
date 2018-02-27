package lab4.library.ganre;

import lab4.library.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer genreId;

    @Column(unique = true)
    private String ganreName;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    Genre() {}

    public Genre(String ganreName) {
        this.ganreName = ganreName;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGanreName() {
        return ganreName;
    }

    public void setGanreName(String ganreName) {
        this.ganreName = ganreName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
