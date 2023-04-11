package ar.edu.mercadoflux.app.core.exception;

public class NoStockException extends CoreException {
    public NoStockException(String productId) {
        super("insufficient_stock", "No stock available for product " + productId);
    }
}
