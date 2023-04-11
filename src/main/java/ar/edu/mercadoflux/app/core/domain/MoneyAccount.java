package ar.edu.mercadoflux.app.core.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MoneyAccount {

    private User user;
    private BigDecimal balance;

    public MoneyAccount addToBalance(BigDecimal amount) {
        return this.toBuilder()
                .balance(balance.add(amount))
                .build();
    }

    public MoneyAccount subtractFromBalance(BigDecimal amount) {
        return this.toBuilder()
                .balance(balance.subtract(amount))
                .build();
    }
}
