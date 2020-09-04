package com.vvp.sample.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.google.gson.Gson;
import com.vvp.sample.model.AccountResponse;

@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * Logger resource.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * Handle all exceptions.
     * @param ex exception
     * @param request web request
     * @return account response entity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<AccountResponse> handleAllExceptions(final Exception ex, final WebRequest request) {
        LOGGER.error("handleAllExceptions()", ex);
        String exMessage = (ex != null) ? ex.getMessage() : null;
        if (exMessage != null) {
            AccountResponse accountResponse = new Gson()
                    .fromJson(ex.getMessage(), AccountResponse.class);
            accountResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            return new ResponseEntity<AccountResponse>(accountResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return null;
        }
    }

    /**
     * Handle illegal argument exception.
     * @param ex illegal argument exception
     * @param request web request
     * @return account response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<AccountResponse> handleIllegalArgumentExceptions(
            final IllegalArgumentException ex, final WebRequest request) {
        LOGGER.error("handleIllegalArgumentExceptions()", ex);
        String exMessage = (ex != null) ? ex.getMessage() : null;
        if (exMessage != null) {
            AccountResponse accountResponse = new Gson()
                    .fromJson(ex.getMessage(), AccountResponse.class);
            accountResponse.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<AccountResponse>(accountResponse, HttpStatus.BAD_REQUEST);
        } else {
            return null;
        }
    }

    /**
     * Handle empty result data access exception.
     * @param ex empty result data access exception
     * @param request web request
     * @return account response entity
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<AccountResponse> handleEmptyResultDataAccessExceptions(
            final EmptyResultDataAccessException ex, final WebRequest request) {
        LOGGER.error("handleEmptyResultDataAccessExceptions()", ex);
        String exMessage = (ex != null) ? ex.getMessage() : null;
        if (exMessage != null) {
            AccountResponse accountResponse = new Gson()
                    .fromJson(exMessage, AccountResponse.class);
            accountResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
            return new ResponseEntity<AccountResponse>(accountResponse, HttpStatus.OK);
        } else {
            return null;
        }
    }
}
