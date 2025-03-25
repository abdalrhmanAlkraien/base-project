package com.iqvia.error.exception;

import com.iqvia.error.error.ErrorCategories;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:50 PM
 */
public class ApiException extends RuntimeException {


    public ApiException(final ErrorCategories errorCategory, final Throwable cause) {
        super(errorCategory.getMessage(), cause);
    }

    public ApiException(final ErrorCategories errorCategory) {
        this(errorCategory, null);
    }
}
