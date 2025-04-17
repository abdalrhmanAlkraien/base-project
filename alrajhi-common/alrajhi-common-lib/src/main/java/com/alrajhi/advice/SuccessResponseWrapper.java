package com.alrajhi.advice;

import lombok.Builder;
import lombok.Data;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/17/2025
 * @Time: 12:31 PM
 */
@Data
@Builder
public class SuccessResponseWrapper<T> {

    private Boolean success;
    private String message;
    private String requestPath;
    private int status;
    private String httpMethod;
    private T data;
}
