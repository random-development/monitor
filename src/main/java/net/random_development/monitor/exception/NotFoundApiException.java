package net.random_development.monitor.exception;

import org.springframework.http.HttpStatus;

public class NotFoundApiException extends ApiException {

    private static final HttpStatus defaultStatus = HttpStatus.NOT_FOUND;

    private static final String defaultMessage = "Not found.";

    public NotFoundApiException() {
        super(defaultMessage, defaultStatus);
    }

    public NotFoundApiException(String message) {
        super(message, defaultStatus);
    }
}
