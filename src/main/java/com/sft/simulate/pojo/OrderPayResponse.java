package com.sft.simulate.pojo;


import lombok.Data;

@Data
public class OrderPayResponse {


    /**
     * 用户id
     */
    private Integer memberId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品数量
     */
    private int goodsNum;

    /**
     * 收货地址Id
     */
    private Integer addressId;

    /**
     * 支付方式
     */
    private Integer paymentId;

    /**
     * 配送方式
     */
    private Integer shippingId;

    /**
     * 配送时间
     */
    private String shipDay;

    /**
     * 配送日期
     */
    private String shipTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否显示商品数量
     */
    private int showCartData;
}
