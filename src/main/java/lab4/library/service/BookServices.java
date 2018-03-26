package lab4.library.service;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    public Collection<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Collection<Book> saveBooks(Set<Book> books) {
        return bookRepository.save(books);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book editBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public Book findBook(Integer id) {
        return bookRepository.findOne(id);
    }

    public List<Book> findByYearAndGenreName(String genreName, int year) {
        return bookRepository.findByYearAndGenreName(genreName, year);
    }

    public List<Book> findByAuthorAndGenreName(String firstName, String lastName, String genreName) {
        return bookRepository.findByAuthorAndGenreName(firstName, lastName, genreName);
    }

    public List<Book> searching(String bookName,
                                String isbn,
                                Integer year,
                                String genreName,
                                String firstName,
                                String lastName,
                                String middleName,
                                String publisherName) {

        List<Book> books = new ArrayList<>();

        if (bookName.compareTo("") != 0) {
            books = bookRepository.findByBookName(bookName);
        } else if (year != null) {
            books = bookRepository.findByYear(year);
        } else if (genreName.compareTo("") != 0) {
            Genre genre = genreService.findByGenreName(genreName);
            for (Book b: genre.getBooks()) {
                books.add(b);
            }
        } else if (firstName.compareTo("") != 0 && lastName.compareTo("") != 0) {
            Author author = authorService.findByFirstNameAndLastName(firstName, lastName);
            for (Book b: author.getBooks()) {
                books.add(b);
            }
        } else if (publisherName.compareTo("") != 0) {
            Publisher publisher = publisherService.findByPublisherName(publisherName);
            for (Book b: publisher.getBooks()) {
                books.add(b);
            }
        }

        if (bookName.compareTo("") != 0 && books.size() != 0) {
            Iterator<Book> iterator = books.iterator();

            while (iterator.hasNext()) {
                if (iterator.next().getBookName().compareTo(bookName) != 0) {
                    iterator.remove();
                }
            }
        }

        if (year != null && books.size() != 0) {
            Iterator<Book> iterator = books.iterator();

            while (iterator.hasNext()) {
                if (iterator.next().getYear() != year) {
                    iterator.remove();
                }
            }
        }

        if (genreName.compareTo("") != 0 && books.size() != 0) {
            Iterator<Book> iterator = books.iterator();
            first:
            while (iterator.hasNext()) {
                Set<Genre> genreSet = iterator.next().getGenres();
                int i = 0;
                for (Genre genre: genreSet) {
                    i++;
                    if (genre.getGenreName().compareTo(genreName) == 0) {
                        break first;
                    } else if (i == genreSet.size()) {
                        iterator.remove();
                    }
                }
            }
        }

        if (firstName.compareTo("") != 0 && lastName.compareTo("") != 0 && books.size() != 0) {
            Iterator<Book> iterator = books.iterator();
            first:
            while (iterator.hasNext()) {
                Set<Author> authorSet = iterator.next().getAuthors();
                int i = 0;
                for (Author author: authorSet) {
                    i++;
                    if (author.getFirstName().compareTo(firstName) == 0 && author.getLastName().compareTo(lastName) == 0) {
                        break first;
                    } else if (i == authorSet.size()) {
                        iterator.remove();
                    }
                }
            }
        }

        if (publisherName.compareTo("") != 0 && books.size() != 0) {
            Iterator<Book> iterator = books.iterator();
            first:
            while (iterator.hasNext()) {
                Set<Publisher> publisherSet = iterator.next().getPublishers();
                int i = 0;
                for (Publisher publisher: publisherSet) {
                    i++;
                    if (publisher.getPublisherName().compareTo(publisherName) == 0) {
                        break first;
                    } else if (i == publisherSet.size()) {
                        iterator.remove();
                    }
                }
            }
        }
        return books;
    }

    public List<Book> findBook(Example<Book> example) {
        return bookRepository.findAll(example);
    }
}
