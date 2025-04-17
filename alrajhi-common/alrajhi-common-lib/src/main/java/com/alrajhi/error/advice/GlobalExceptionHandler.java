package com.alrajhi.error.advice;

import com.alrajhi.error.error.ApiCallError;
import com.alrajhi.error.error.ErrorCategories;
import com.alrajhi.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:42 PM
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ApiCallError>
    handleNotFoundError(HttpServletRequest request, Exception ex) {

        log.error(
                "handle not found request for request: {} , and the error is: {} \n",
                request.getRequestURI(),
                ex.getCause().getMessage());

        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ApiCallError<>(
                        ErrorCategories.NOT_FOUND,
                        WebUtils.getCurrentRequestUrl(),
                        List.of("The URL is wrong. Please try again ".concat(request.getRequestURI()))));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiCallError>
    handleInternalServerError(HttpServletRequest request, Exception ex) {

        log.error(
                "handle Internal Server Error for request: {} , and the error is: {} \n",
                request.getRequestURI(),
                ex.getCause().getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiCallError<>(
                        ErrorCategories.INTERNAL_SERVER_ERROR,
                        WebUtils.getCurrentRequestUrl(),
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ApiCallError>
    handleIllegalArgumentException(HttpServletRequest request,
                                   IllegalArgumentException ex) {

        log.error("handle Illegal Argument Exception for request: {} , and the error is: {} \n",
                request.getRequestURI(),
                ex.getCause().getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>(
                        ErrorCategories.ILLEGAL_ARGUMENT,
                        WebUtils.getCurrentRequestUrl(),
                        List.of(ex.getMessage())));
    }



}
