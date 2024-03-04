package tw.com.remecomic.money.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException() {
        super("Insufficient balance to perform the operation.");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
