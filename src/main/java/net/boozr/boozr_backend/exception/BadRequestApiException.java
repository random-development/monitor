package net.boozr.boozr_backend.exception;

import org.springframework.http.HttpStatus;

public class BadRequestApiException extends ApiException {

    private static final HttpStatus defaultStatus = HttpStatus.BAD_REQUEST;

    private static final String defaultMessage = "Bad request.";

    public BadRequestApiException() {
        super(defaultMessage, defaultStatus);
    }

    public BadRequestApiException(String message) {
        super(message, defaultStatus);
    }
}
