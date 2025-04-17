package com.alrajhi.advice;

import com.alrajhi.error.error.ErrorCategories;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/17/2025
 * @Time: 12:31 PM
 */
@Data
@Builder
public class ErrorResponseWrapper<T> {

    private Boolean success;
    private ErrorCategories errorCode;
    private String requestPath;
    private String httpMethod;
    private int status;
    @SuppressWarnings("unchecked")
    private List<T> errorMessage;
}
