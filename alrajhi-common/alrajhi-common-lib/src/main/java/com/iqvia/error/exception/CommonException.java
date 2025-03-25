package com.iqvia.error.exception;

import com.iqvia.error.error.BusinessErrorCodes;
import com.iqvia.error.error.ErrorCategories;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 5:51 PM
 */
public class CommonException extends ApiException {

    private BusinessErrorCodes businessErrorCode;


    public CommonException(final ErrorCategories errorCategories, final BusinessErrorCodes businessErrorCode) {
        super(errorCategories);
        this.businessErrorCode = businessErrorCode;
    }
}
