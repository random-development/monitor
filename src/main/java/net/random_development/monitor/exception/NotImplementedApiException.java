package net.random_development.monitor.exception;

import org.springframework.http.HttpStatus;

public class NotImplementedApiException extends ApiException {

    private static final HttpStatus defaultStatus = HttpStatus.NOT_IMPLEMENTED;

    private static final String defaultMessage = "Not Implemented.";

    public NotImplementedApiException() {
        super(defaultMessage, defaultStatus);
    }

    public NotImplementedApiException(String message) {
        super(message, defaultStatus);
    }
}
