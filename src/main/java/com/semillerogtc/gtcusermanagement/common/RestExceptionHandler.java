package com.semillerogtc.gtcusermanagement.common;

import com.semillerogtc.gtcusermanagement.domain.InvalidEmailException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(InvalidEmailException invalidEmailException) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, invalidEmailException);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("message", errors);
        return errorResponse;
    }

    private ResponseEntity<ExceptionResponse> buildResponseEntity(HttpStatus httpStatus, Exception ex) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
}
