package com.danipl.st.api.controller.handler;

import com.danipl.st.exception.ApiException;
import com.google.common.collect.Maps;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException exception, final WebRequest request) {

        final ApiException apiException = (ApiException) exception;

        return super.handleExceptionInternal(
                apiException,
                Maps.immutableEntry("generic", apiException.getMessage()),
                new HttpHeaders(),
                apiException.getHttpStatus(),
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {

        final Map<String, String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .filter(error -> error.getDefaultMessage() != null)
                .collect(
                        Collectors.toMap(
                                error -> ((FieldError) error).getField(),
                                DefaultMessageSourceResolvable::getDefaultMessage));

        return super.handleExceptionInternal(ex, errors, headers, status, request);
    }

}