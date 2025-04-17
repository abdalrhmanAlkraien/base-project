package com.alrajhi.error.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:46 PM
 */
@Getter
@AllArgsConstructor
public enum ErrorCategories {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "Illegal Arguments"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    MISSING_REQUEST_MANDATORY_FIELD(HttpStatus.BAD_REQUEST, "Missing a mandatory request field"),
    BUSINESS_ROLE_ERROR(HttpStatus.BAD_REQUEST, "Business role violation");

    private final HttpStatus httpStatus;
    private final String message;
}
