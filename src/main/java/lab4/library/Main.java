package lab4.library;

import lab4.library.author.Author;
import lab4.library.ganre.Genre;
import lab4.library.repository.BookRepository;
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

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }*/

    public static void main(String[] args) {
        System.out.println("test");
        SpringApplication.run(Main.class, args);
    }

    /*@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/webapp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }*/


    @Bean
    CommandLineRunner runner2(BookRepository bookRepository) {
        Set<Author> authorSet = new HashSet<>();
        authorSet.add(new Author("Иван", "Иванов" , "Иванович"));
        authorSet.add(new Author("Петр", "Петров" , "Петрович"));

        Set<Publisher> publisherSet = new HashSet<>();
        publisherSet.add(new Publisher("Дрофа"));
        publisherSet.add(new Publisher("Новый свет"));

        Set<Genre> genres1 = new HashSet<>();
        genres1.add(new Genre("Автобиография"));

        Set<Genre> genres2 = new HashSet<>();
        genres2.add(new Genre("Сказки"));

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(new Book("Биография Петра и Ивана", "978-5-389-06696-1", 2016, genres1, authorSet, publisherSet));
        bookSet.add(new Book("Сказки Петра и Ивана", "978-5-389-06696-0", 2000, genres2, authorSet, publisherSet));

        return args -> bookRepository.save(bookSet);
    }

    @Bean
    CommandLineRunner runner3(BookRepository bookRepository) {
        return args -> bookRepository.findAll();
    }
}
