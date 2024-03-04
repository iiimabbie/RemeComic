package tw.com.remecomic.money.exception;

public class CouponException extends RuntimeException{
    public CouponException() {
        super("User account not found for user ID: ");
    }

    public CouponException(String message) {
        super(message);
    }
}
