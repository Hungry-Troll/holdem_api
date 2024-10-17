package net.lodgames.config.error.exception;


import net.lodgames.config.error.ErrorCode;

public class LoginFailureException extends RestException {
    public LoginFailureException() {
        super();
    }

    public LoginFailureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
