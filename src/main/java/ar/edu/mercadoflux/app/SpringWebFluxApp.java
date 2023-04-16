package ar.edu.mercadoflux.app;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@Log4j2
@EnableReactiveMongoRepositories
public class SpringWebFluxApp {
    public static void main(String[] args) {
        log.info("Starting application...");
        SpringApplication.run(SpringWebFluxApp.class, args);
    }
}