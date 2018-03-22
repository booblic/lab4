package lab4.library;

import lab4.library.author.Author;
import lab4.library.book.Book;
import lab4.library.genre.Genre;
import lab4.library.publisher.Publisher;
import lab4.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Main {

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
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator(ResourcePatternResolver resourcePatternResolver) throws IOException {

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();

        factory.setResources(resourcePatternResolver.getResources("classpath:/import/*.json"));
        return factory;
    }

/*    @Bean
    CommandLineRunner runner2(BookRepository bookRepository) {
        Set<Author> authorSet1 = new HashSet<>();
        authorSet1.add(new Author("Иван", "Иванов" , "Иванович"));
        authorSet1.add(new Author("Петр", "Петров" , "Петрович"));

        Set<Author> authorSet2 = new HashSet<>();
        authorSet2.add(new Author("Александр", "Пушкин" , "Сергеевич"));

        Set<Author> authorSet3 = new HashSet<>();
        authorSet3.add(new Author("Михаил", "Лермонтов" , "Юрьевич"));

        Set<Publisher> publisherSet1 = new HashSet<>();
        publisherSet1.add(new Publisher("Новый свет"));
        publisherSet1.add(new Publisher("Дрофа"));

        Set<Publisher> publisherSet2 = new HashSet<>();
        publisherSet2.add(new Publisher("Эксмо"));

        Set<Genre> genres1 = new HashSet<>();
        genres1.add(new Genre("Автобиография"));

        Set<Genre> genres2 = new HashSet<>();
        genres2.add(new Genre("Сказки"));

        Set<Genre> genres3 = new HashSet<>();
        genres3.add(new Genre("Роман"));

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(new Book("Биография Петра и Ивана", "978-5-389-06696-1", 2016, genres1, authorSet1, publisherSet1));
        bookSet.add(new Book("Сказки Петра и Ивана", "978-5-389-06696-0", 2000, genres2, authorSet1, publisherSet1));
        bookSet.add(new Book("Евгений Онегин", "978-5-699-95653-1", 2017, genres3, authorSet2, publisherSet2));
        bookSet.add(new Book("Герой нашего времени", "978-5-4335-0125-6", 2000, genres3, authorSet3, publisherSet2));

        return args -> bookRepository.save(bookSet);
    }*/
}
