package net.lodgames.config.error.exception;

import net.lodgames.config.error.ErrorCode;

public class NotFoundException extends RestException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
