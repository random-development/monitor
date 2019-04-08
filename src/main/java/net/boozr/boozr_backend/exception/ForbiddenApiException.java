package net.boozr.boozr_backend.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenApiException extends ApiException {

    private static final HttpStatus defaultStatus = HttpStatus.FORBIDDEN;

    private static final String defaultMessage = "Forbidden.";

    public ForbiddenApiException() {
        super(defaultMessage, defaultStatus);
    }

    public ForbiddenApiException(String message) {
        super(message, defaultStatus);
    }
}
