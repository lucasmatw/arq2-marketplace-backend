package ar.edu.mercadoflux.app.core.exception;

public class ProductNotFoundException extends CoreException {
    public ProductNotFoundException() {
        super("product_not_found", "Product not found");
    }
}
