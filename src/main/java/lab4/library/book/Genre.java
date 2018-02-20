package lab4.library.book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer genreId;

    private String name;

    @OneToMany(mappedBy = "genre")
    private Set<Book> books = new HashSet<>();

    Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
