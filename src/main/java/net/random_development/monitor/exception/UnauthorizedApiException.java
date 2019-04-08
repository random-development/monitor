package net.random_development.monitor.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedApiException extends ApiException {

    private static final HttpStatus defaultStatus = HttpStatus.UNAUTHORIZED;

    private static final String defaultMessage = "Unauthorized.";

    public UnauthorizedApiException() {
        super(defaultMessage, defaultStatus);
    }

    public UnauthorizedApiException(String message) {
        super(message, defaultStatus);
    }
}
