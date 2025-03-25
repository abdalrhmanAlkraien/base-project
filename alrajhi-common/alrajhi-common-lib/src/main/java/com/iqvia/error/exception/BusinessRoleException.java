package com.iqvia.error.exception;

import com.iqvia.error.error.BusinessErrorCodes;
import com.iqvia.error.error.ErrorCategories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:54 PM
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessRoleException extends CommonException {

    public BusinessRoleException(final BusinessErrorCodes businessErrorCode) {
        super(ErrorCategories.BUSINESS_ROLE_ERROR, businessErrorCode);
    }
}
