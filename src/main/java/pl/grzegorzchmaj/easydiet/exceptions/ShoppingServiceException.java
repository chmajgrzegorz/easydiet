package pl.grzegorzchmaj.easydiet.exceptions;

import java.util.function.Supplier;

public class ShoppingServiceException extends Exception {

    public ShoppingServiceException(String message) {
        super(message);
    }
}
