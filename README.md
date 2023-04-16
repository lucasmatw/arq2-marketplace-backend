# Arquitectura de Software II (UNQ)

## Mercadoflux

### Tecnología

- Java 17
- Spring WebFlux
- MongoDB (reactive driver)

### Ejecución

#### Local con Docker

Se necesita tener `mvn` y `docker-compose` instalados.

Generar el jar:
- `mvn package -DskipTests`

Levantar docker-compose (levanta la app y la DB):
- `docker-compose up --build`

Levantarla:
- `docker run -e MONGO_ATLAS_DB_PASSWORD=$MONGO_ATLAS_DB_PASSWORD -e MONGO_ATLAS_DB_USER=$MONGO_ATLAS_DB_USER -p 8080:8080 mercadoflux-api:spring-docker`


#### Local con Mongo Atlas

Setear variables de entorno `MONGO_ATLAS_DB_PASSWORD` y `MONGO_ATLAS_DB_USER` con los valores correspondientes.


