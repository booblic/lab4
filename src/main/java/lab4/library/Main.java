package lab4.library;

import lab4.library.author.Author;
import lab4.library.genre.Genre;
import lab4.library.repository.BookRepository;
import lab4.library.book.*;
import lab4.library.publisher.Publisher;
import lab4.library.repository.UserRepository;
import lab4.library.user.Role;
import lab4.library.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    // XXX: N2 split json files, save Roles, then save Users, disable cascade MERGE/PESIST
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator(ResourcePatternResolver resourcePatternResolver) throws IOException {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        // Set a custom ObjectMapper if Jackson customization is needed
        //factory.setObjectMapper(…);
        factory.setResources(resourcePatternResolver.getResources("classpath:/import/*.json"));
        return factory;
    }

//    @Bean
//    CommandLineRunner runner2(BookRepository bookRepository) {
//        Set<Author> authorSet = new HashSet<>();
//        authorSet.add(new Author("Иван", "Иванов" , "Иванович"));
//        authorSet.add(new Author("Петр", "Петров" , "Петрович"));
//
//        Set<Publisher> publisherSet = new HashSet<>();
//        publisherSet.add(new Publisher("Дрофа"));
//        publisherSet.add(new Publisher("Новый свет"));
//
//        Set<Genre> genres1 = new HashSet<>();
//        genres1.add(new Genre("Автобиография"));
//
//        Set<Genre> genres2 = new HashSet<>();
//        genres2.add(new Genre("Сказки"));
//
//        Set<Book> bookSet = new HashSet<>();
//        bookSet.add(new Book("Биография Петра и Ивана", "978-5-389-06696-1", 2016, genres1, authorSet, publisherSet));
//        bookSet.add(new Book("Сказки Петра и Ивана", "978-5-389-06696-0", 2000, genres2, authorSet, publisherSet));
//
//        return args -> bookRepository.save(bookSet);
//    }

//    @Bean
//    CommandLineRunner runner3(UserRepository userRepository) {
//        Role role1 = new Role();
//        role1.setRoleName("Users");
//        role1.setAuthority("ROLE_USER");
//
//        Role role2 = new Role();
//        role2.setRoleName("Administrators");
//        role2.setAuthority("ROLE_ADMIN");
//
//        Set<Role> roleSet1 = new HashSet<>();
//        roleSet1.add(role1);
//        roleSet1.add(role2);
//
//        User user1 = new User();
//        user1.setUsername("admin");
//        user1.setPassword("$2a$10$ks.dpu2YDc5BrpoZwR6T2e.l5EScUuSuyYDoxtuCx3nVU2YVCVlA.");
//        user1.setFirstName("Admin");
//        user1.setLastName("Adminov");
//        user1.setMiddleName("Adminovich");
//        user1.setEmail("admin@yandex.ru");
//        user1.setPhoneNumber("88005553535");
//        user1.setRoles(roleSet1);
//
//        Set<Role> roleSet2 = new HashSet<>();
//        roleSet2.add(role1);
//
//        User user2 = new User();
//        user2.setUsername("kirill95");
//        user2.setPassword("$2a$10$iQvmZ5iM8XoLigBi4oHJaep4guB5iXchg7cdqyy4jR.ZT8H3m.QJy");
//        user2.setFirstName("Kirill");
//        user2.setLastName("Terentev");
//        user2.setMiddleName("Aleksandrovich");
//        user2.setEmail("johnyXY@yandex.ru");
//        user2.setPhoneNumber("88005553535");
//        user2.setRoles(roleSet2);
//
//        List<User> userList = new ArrayList<>();
//        userList.add(user1);
//        userList.add(user2);
//        return args -> userRepository.save(userList);
//    }
}
