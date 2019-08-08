package com.sft.simulate.enums.payment;

public enum PayStatusEnum {

    PAY_READY(0,"待支付"),
    PAY_SUCCESS(1,"支付成功"),
    PAY_ONGOING(2,"支付中"),
    PAY_FAIL(-1,"支付失败");

    PayStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
