package lab4.library.service;

import lab4.library.genre.Genre;
import lab4.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that implements business logic for genre
 * @author Кирилл
 * @version 1.0
 */
@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    public Genre findByGenreName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }
}
