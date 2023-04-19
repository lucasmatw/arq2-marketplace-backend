package ar.edu.mercadoflux.app.ports.input.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PingController {
    
    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }
}
