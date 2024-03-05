package dbproject.ownpli.exception;

import lombok.Getter;

@Getter
public class OwnPliForbiddenException extends RuntimeException {

    public OwnPliForbiddenException(String message) {
        super(message);
    }
}
