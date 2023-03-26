package ar.edu.mercadogratis.app.controller;


import ar.edu.mercadogratis.app.model.AddFundsRequest;
import ar.edu.mercadogratis.app.model.User;
import ar.edu.mercadogratis.app.service.MoneyAccountService;
import ar.edu.mercadogratis.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/money_account")
@RequiredArgsConstructor
public class MoneyAccountController {

    private final MoneyAccountService moneyAccountService;
    private final UserService userService;

    @PostMapping
    public Mono<BigDecimal> addFunds(@RequestBody AddFundsRequest addFundsRequest) {
        return getUser(addFundsRequest.getUserId())
                .flatMap(user -> moneyAccountService.creditAmount(user, addFundsRequest.getAmount()));
    }

    @GetMapping
    public Mono<BigDecimal> getFunds(@RequestParam Long userId) {
        return getUser(userId)
                .flatMap(moneyAccountService::getFunds);
    }

    private Mono<User> getUser(Long userId) {
        return userService.getUser(userId);
    }
}
