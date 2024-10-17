package net.lodgames.config.error.exception;


import net.lodgames.config.error.ErrorCode;

public class InvalidAuthException extends RestException {
    public InvalidAuthException() {
        super();
    }

    public InvalidAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
