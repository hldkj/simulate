package com.sft.simulate.enums.payment;

public enum PayTypeEnum {


    AliPayH5(1,"支付宝H5"),
    AliPayScan(2,"支付宝扫码支付"),
    WeChatH5(3,"微信H5");


    PayTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    private int code;


    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
