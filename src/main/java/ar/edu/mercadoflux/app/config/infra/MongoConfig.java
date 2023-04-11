package ar.edu.mercadoflux.app.config.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "ar.edu.mercadoflux.app.ports.output.persistence")
public class MongoConfig {
}