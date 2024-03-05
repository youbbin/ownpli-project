package dbproject.ownpli.exception;

import lombok.Getter;

@Getter
public class OwnPliException extends RuntimeException {

    public OwnPliException(String message) {
        super(message);
    }
}
