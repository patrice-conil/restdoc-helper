package com.orange.otml.controller;

import com.orange.otml.exception.SampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Performs globally the com.orange.otml.exception handling. The exceptions below could be raised by any
 * com.orange.otml.controller and they would be handled here, if not handled in the com.orange.otml.controller
 * already.
 *
 */
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    /**
     * Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerAdvice.class);

    /**
     * Resource Not found handler.
     * @param exception the com.orange.otml.exception
     * @return NotFound Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(SampleException exception) {
        logger.warn("OTMLException: ", exception.getMessage());
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
