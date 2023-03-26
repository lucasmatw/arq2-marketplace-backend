package ar.edu.mercadogratis.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class MoneyAccount {

    private User user;

    private BigDecimal balance;

    public void addToBalance(BigDecimal amount){
        this.balance = balance.add(amount);
    }

    public void subtractFromBalance(BigDecimal amount){
        this.balance = balance.subtract(amount);
    }
}
