package ar.edu.mercadoflux.app.ports.input.web.controller;

import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PingController {

    private final ObjectMapper objectMapper;

    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }

    @PostMapping("/ping_long")
    public Mono<Long> pingLong() {
        return Mono.just(2L);
    }

    @SneakyThrows
    @GetMapping("/ping_json")
    public Mono<RegisterUser> pingJson() {

        String jsonUser = "{\n" +
                "    \"name\" : \"lucases\",\n" +
                "    \"password\" : \"2333\",\n" +
                "    \"last_name\" : \"matsweww\",\n" +
                "    \"email\" : \"lucas2.matw@gmail.com\",\n" +
                "    \"cuit\" : \"3333\"\n" +
                "}";

        RegisterUser user = objectMapper.readValue(jsonUser, RegisterUser.class);
        return Mono.just(user);
    }
}
