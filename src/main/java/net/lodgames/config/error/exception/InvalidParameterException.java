package net.lodgames.config.error.exception;


import net.lodgames.config.error.ErrorCode;

public class InvalidParameterException extends RestException {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(ErrorCode errorCode) {
        super(errorCode);
    }
}