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
        SpringApplication.run(Main.class, args);
    }

    /**
     * Method for the population of the repository data from json files
     * @throws IOException
     * @param resourcePatternResolver - strategy interface for resolving a location pattern into Resource objects
     * @return factory
     */
/*    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator(ResourcePatternResolver resourcePatternResolver) throws IOException {

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();

        factory.setResources(resourcePatternResolver.getResources("classpath:/import/*.json"));

        return factory;
    }*/

    /**
     * Method for get a class object for client-side HTTP access
     * @return class object for client-side HTTP access
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
