package lab4.library.service;

import lab4.library.genre.Genre;
import lab4.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre findByGenreName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }
}
