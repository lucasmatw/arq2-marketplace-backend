# Arquitectura de Software II (UNQ)

## Mercadoflux

### Tecnología

- Java 17
- Spring WebFlux
- MongoDB (reactive driver)

### Ejecución

#### Set up

Setear variables de entorno `MONGO_ATLAS_DB_PASSWORD` y `MONGO_ATLAS_DB_USER` con los valores correspondientes.

#### Local con Docker

Generar el jar:
- `mvn install` 

Generar la imagen de Docker:
- `docker build -t mercadoflux-api:spring-docker` 

Levantarla:
- `docker run -e MONGO_ATLAS_DB_PASSWORD=$MONGO_ATLAS_DB_PASSWORD -e MONGO_ATLAS_DB_USER=$MONGO_ATLAS_DB_USER -p 8080:8080 mercadoflux-api:spring-docker`

