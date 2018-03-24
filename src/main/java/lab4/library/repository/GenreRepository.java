package lab4.library.repository;

import lab4.library.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>, QueryByExampleExecutor<Genre> {

    Genre findByGenreName(String genreName);
}
