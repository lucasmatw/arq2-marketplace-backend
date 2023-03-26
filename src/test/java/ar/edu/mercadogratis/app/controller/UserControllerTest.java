package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.model.User;
import ar.edu.mercadogratis.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() {

        // given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = String.format("http://localhost:%s/user/register", port);

        User user = User.builder()
                .name("a name")
                .lastName("a lastname")
                .email("an_email@mail.com")
                .cuit("22332233")
                .build();
        
        HttpEntity<User> request =
                new HttpEntity<>(user, headers);

        // when
        ResponseEntity<Long> response = restTemplate.postForEntity(url, request, Long.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        User createdUser = userService.getUser(response.getBody()).block();
        assertThat(createdUser).isNotNull();
    }

    @Test
    void testLoginUser() {

        // given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = String.format("http://localhost:%s/user/login", port);

        User user = User.builder()
                .name("a name")
                .lastName("a lastname")
                .email("an_email@mail.com")
                .cuit("22332233")
                .password("12345")
                .build();

        userService.createUser(user);
        Mono<User> savedUser = userService.getUserForMail("an_email@mail.com");

        HttpEntity<User> request =
                new HttpEntity<>(savedUser.block(), headers);

        // when
        ResponseEntity<User> response = restTemplate.postForEntity(url, request, User.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
