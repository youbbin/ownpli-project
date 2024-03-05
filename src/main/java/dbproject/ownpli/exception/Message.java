package dbproject.ownpli.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Message {

    private String message;
    private HttpStatus httpStatus;

    public static Message of(String message, HttpStatus status) {
        return Message.builder()
                .message(message)
                .httpStatus(status)
                .build();
    }

}
