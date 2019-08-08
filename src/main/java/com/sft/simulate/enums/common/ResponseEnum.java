package com.sft.simulate.enums.common;

public enum ResponseEnum {

    SUCCESS(1000, "成功"),
    FAIL(0001, "失败"),
    SIGN_ERROR(0002, "签名错误"),
    PARAM_IS_ERROR(1001, "参数异常"),
    UP_SYSTEM_ERROR(9998, "服务繁忙"), // 上游响应超时
    SYSTEM_ERROR(9999, "系统异常");

    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
