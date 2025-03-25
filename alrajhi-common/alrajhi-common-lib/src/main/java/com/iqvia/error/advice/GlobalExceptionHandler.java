package com.iqvia.error.advice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iqvia.util.GenericResponse;
import com.iqvia.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:42 PM
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericResponse<ApiCallError>>
    handleInternalServerError(HttpServletRequest request, Exception ex) {

        log.error("handleInternalServerError {} \n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(GenericResponse.error(
                        new ApiCallError<>(
                                "Internal server error",
                                WebUtils.getCurrentRequestUrl(),
                                List.of(ex.getMessage()))));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<GenericResponse<ApiCallError>>
    handleIllegalArgumentException(HttpServletRequest request,
                                   IllegalArgumentException ex) {

        log.error("IllegalArgumentException {} \n {}",
                request.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(GenericResponse.error(
                        new ApiCallError<>(
                                "Illegal Arguments Please try again",
                                WebUtils.getCurrentRequestUrl(),
                                List.of(ex.getMessage()))
                ));
    }

    @JsonRootName("ApiError")
    record ApiCallError<T>(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("url") String url,
            @JsonProperty("details") List<T> details) {
    }
}
