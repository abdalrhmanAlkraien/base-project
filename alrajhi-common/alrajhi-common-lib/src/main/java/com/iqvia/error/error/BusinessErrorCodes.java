package com.iqvia.error.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:47 PM
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorCodes {

    NO_RECORD_FOUND("GEN-001", "No record found."),
    CANT_CALL_INTERNAL_API("GEN-002", "Connection Issue");

    private String code;
    private String errorMessage;
}
