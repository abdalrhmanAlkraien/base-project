package com.alrajhi.error.exception;

import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.error.ErrorCategories;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:54 PM
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BusinessRoleException extends CommonException {

    private final BusinessErrorCodes errorCode;

    public BusinessRoleException(final BusinessErrorCodes businessErrorCode) {
        super(ErrorCategories.BUSINESS_ROLE_ERROR, businessErrorCode);
        this.errorCode = businessErrorCode;
    }
}
