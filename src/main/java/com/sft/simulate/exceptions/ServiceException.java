package com.sft.simulate.exceptions;

import com.sft.simulate.enums.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by yuyidi on 2019-04-15.
 * @desc
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

}
