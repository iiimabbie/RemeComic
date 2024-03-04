package tw.com.remecomic.money.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.com.remecomic.money.exception.InsufficientBalanceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
//        return new ResponseEntity<>("Insufficient balance to perform the operation.", HttpStatus.BAD_REQUEST);
//    }
}
