package ar.edu.mercadoflux.app.core.exception;

public class MoneyAccountNotFoundException extends CoreException{
    public MoneyAccountNotFoundException() {
        super("money_account_not_found", "Money account not found");
    }
}
