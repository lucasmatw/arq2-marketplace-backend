package ar.edu.mercadoflux.app.core.exception;

public class InsufficientFundsException extends CoreException {
    public InsufficientFundsException() {
        super("insufficient_funds", "Insufficient funds to complete the operation");
    }
}
