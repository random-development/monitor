package net.random_development.monitor.controller.advice;

import net.random_development.monitor.dto.ErrorMessageDto;
import net.random_development.monitor.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    private static final ApiException defaultException = new ApiException();

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorMessageDto> exceptionHandler(ApiException e) {
        e.printStackTrace();
        return toResponseEntity(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> exceptionHandler(Exception e) {
        e.printStackTrace();
        return toResponseEntity(defaultException);
    }

    private ResponseEntity<ErrorMessageDto> toResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(toErrorMessageDto(apiException), apiException.getStatus());
    }

    private ErrorMessageDto toErrorMessageDto(ApiException apiException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        errorMessageDto.setMessage(apiException.getMessage());
        return errorMessageDto;
    }
}
