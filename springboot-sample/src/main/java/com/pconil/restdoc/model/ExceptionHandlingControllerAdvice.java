/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
