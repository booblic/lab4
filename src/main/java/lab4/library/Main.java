package lab4.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("test");
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator(ResourcePatternResolver resourcePatternResolver) throws IOException {

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();

        factory.setResources(resourcePatternResolver.getResources("classpath:/import/*.json"));

        return factory;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
