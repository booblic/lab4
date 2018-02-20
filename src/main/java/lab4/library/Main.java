package lab4.library;

import lab4.library.author.Author;
import lab4.library.book.*;
import lab4.library.publisher.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("test");
        SpringApplication.run(Main.class, args);
    }


    @Bean
    CommandLineRunner runner2(BookRepository bookRepository) {
        Set<Author> authorSet = new HashSet<>();
        authorSet.add(new Author("Иван", "Иванов" , "Иванович"));
        authorSet.add(new Author("Петр", "Петров" , "Петрович"));

        Set<Publisher> publisherSet = new HashSet<>();
        publisherSet.add(new Publisher("Дрофа"));
        publisherSet.add(new Publisher("Новый свет"));

        Genre genre1 = new Genre("Автобиография");
        Genre genre2 = new Genre("Сказки");

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(new Book("Биография Петра и Ивана", "8800-555-35-35", 2016, genre1, authorSet, publisherSet));
        bookSet.add(new Book("Сказки Петра и Ивана", "978-5-389-06696-0", 2000, genre2, authorSet, publisherSet));

        return args -> bookRepository.save(bookSet);
    }
}
