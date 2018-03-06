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

    public Set<Genre> getGenres(String[] genreNames) {

        Set<Genre> genreSet = new HashSet<>();

        for (String genreName: genreNames) {

            if (genreName.compareTo("") != 0) {

                Genre genre = findByGenreName(genreName);

                if (genre != null) {
                    genreSet.add(genre);
                } else {
                    genreSet.add(saveGenre(new Genre(genreName)));
                }
            }
        }
        return genreSet;
    }

    public Genre findByGenreName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }
}
