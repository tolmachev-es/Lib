package org.tolmachev.lib.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tolmachev.lib.exceptions.SubscriptionNotFoundException;
import org.tolmachev.lib.model.ErrorMessage;
import org.tolmachev.lib.model.ValidationErrorMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({
            SubscriptionNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage subscriptionException(SubscriptionNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setError(ex.getMessage());
        return errorMessage;
    }


    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorMessage notValidDtoException(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        List<FieldError> errors = ex.getFieldErrors();
        for (FieldError fe : errors) {
            error.put(fe.getField(), fe.getDefaultMessage());
        }
        ValidationErrorMessage errorMessage = new ValidationErrorMessage();
        errorMessage.setErrors(error);
        return errorMessage;
    }

}
