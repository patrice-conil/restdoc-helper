package com.pconil.restdoc.model;

import com.pconil.restdoc.exception.SampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Performs exception handling globally. The exceptions below could be raised by any
 * controller and they would be handled here, if not handled in the controller.
 */
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    /**
     * Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerAdvice.class);

    /**
     * Resource Not found handler.
     * @param exception the com.pconil.restdoc.exception
     * @return NotFound Error
     */
    @ExceptionHandler(SampleException.class)
    public ResponseEntity<Error> handleException(SampleException exception) {
        logger.warn("OTMLException: ", exception.getMessage());
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
