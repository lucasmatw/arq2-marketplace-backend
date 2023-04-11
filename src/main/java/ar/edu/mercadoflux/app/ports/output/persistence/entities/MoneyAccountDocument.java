package ar.edu.mercadoflux.app.ports.output.persistence.entities;

import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document(collection = "money_accounts")
@Builder
@Data
public class MoneyAccountDocument {
    @MongoId
    private String id;
    private String userId;
    private BigDecimal balance;

    public static MoneyAccountDocument fromMoneyAccount(String id, MoneyAccount moneyAccount) {
        return MoneyAccountDocument.builder()
                .id(id)
                .userId(moneyAccount.getUser().getId())
                .balance(moneyAccount.getBalance())
                .build();
    }

    public MoneyAccount toMoneyAccount(User user) {
        return MoneyAccount.builder()
                .user(user)
                .balance(balance)
                .build();
    }
}
