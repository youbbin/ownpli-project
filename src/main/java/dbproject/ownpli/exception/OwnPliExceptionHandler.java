package dbproject.ownpli.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OwnPliExceptionHandler {

    @ExceptionHandler(OwnPliException.class)
    public ResponseEntity<Message> handler(OwnPliException e) {
        return ResponseEntity.badRequest().body(Message.of(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(OwnPliForbiddenException.class)
    public ResponseEntity<Message> handle(OwnPliForbiddenException e) {
        Message message = new Message(e.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
