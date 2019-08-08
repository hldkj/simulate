package com.sft.simulate.enums.payment;

public enum  PaymentEnum {


    AliPayH5("AliPayH5","支付宝H5"),
    AliPayScan("AliPayScan","支付宝扫码支付"),
    WeChatH5("WeChatH5","微信H5");


    PaymentEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    private String code;


    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
