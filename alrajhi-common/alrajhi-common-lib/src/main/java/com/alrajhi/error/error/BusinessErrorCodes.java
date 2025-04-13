package com.alrajhi.error.error;

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
    CANT_CALL_INTERNAL_API("GEN-002", "Connection Issue"),
    WRONG_LETTER_TYPE("REP-001", "The report letter type is wrong"),
    REPORT_IS_NULL("REP-002", "Report is null"),
    ;

    private final String code;
    private final String errorMessage;
}
