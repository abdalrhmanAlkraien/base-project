package com.alrjhi.error.exception;

import com.alrjhi.error.error.BusinessErrorCodes;
import com.alrjhi.error.error.ErrorCategories;

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
