package com.offer.challenge.controllers.util;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<MessageResponse> processException(MethodArgumentNotValidException ex) {
        List<MessageResponse> errors = new ArrayList<>();

        BindingResult result = ex.getBindingResult();
        for(FieldError error : result.getFieldErrors()) {
            errors.add(new MessageResponse(error.getField(), error.getCode(), error.getDefaultMessage()));
        }
        return errors;
    }
}
