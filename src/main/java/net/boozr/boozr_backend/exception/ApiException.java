package net.boozr.boozr_backend.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private static final HttpStatus defaultStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final String defaultMessage = "Internal server error, please try again.";

    private HttpStatus status;

    public ApiException() {
        this(defaultMessage, defaultStatus);
    }

    public ApiException(String message) {
        this(message, defaultStatus);
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
