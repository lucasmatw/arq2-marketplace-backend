package ar.edu.mercadoflux.app.ports.input.web.controller;


import ar.edu.mercadoflux.app.ports.input.web.dto.AddFundsRequest;
import ar.edu.mercadoflux.app.core.usecase.moneyaccount.AddFundsUseCase;
import ar.edu.mercadoflux.app.core.usecase.moneyaccount.GetFundsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/money_account")
@RequiredArgsConstructor
public class MoneyAccountController {

    private final GetFundsUseCase getFundsUseCase;
    private final AddFundsUseCase addFundsUseCase;

    @PostMapping
    public Mono<BigDecimal> addFunds(@RequestBody AddFundsRequest addFundsRequest) {
        return addFundsUseCase.addFunds(addFundsRequest.getUserId(), addFundsRequest.getAmount());
    }

    @GetMapping
    public Mono<BigDecimal> getFunds(@RequestParam String userId) {
        return getFundsUseCase.getFunds(userId);
    }
}
