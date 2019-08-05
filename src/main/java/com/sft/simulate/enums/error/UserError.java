package com.sft.simulate.enums.error;

/**
 * @author Created by yuyidi on 2019-06-14.
 * @desc
 */
public enum UserError implements ErrorCode {

    NOT_FOUND(1301, "用户不存在"),
    INCORRECT(1302, "用户名或密码错误"),
    ;


    private int code;
    private String mssage;

    UserError(int code, String mssage) {
        this.code = code;
        this.mssage = mssage;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return mssage;
    }
}
