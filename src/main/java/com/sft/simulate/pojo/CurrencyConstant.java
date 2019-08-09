package com.sft.simulate.pojo;

public interface CurrencyConstant {

    String PUBLIC_HOST = "";

    //拉取商品信息
    String PULLOUT_GOODS_URL = "http://192.168.1.104:8080/api/shop/goods/obtain-goods-list.do";

    //拉取会员信息
    String PULLOUT_MEMBER_URL = "http://192.168.1.104:8080/api/shop/member/obtain-member-list.do";

    //会员登录
    String MEMBER_LOGIN_URL = "http://192.168.1.104:8080/api/shop/member/obtain-login-session.do";

    //进行下单
    String PAYMENT_ORDER_URL = "http://192.168.1.104:8080/api/shop/pay/order.do";

    //回调地址
    String PAYMENT_CALLBACK_URL = "http://192.168.1.104:8011/pay/callback";

    //返回地址
    String PAYMENT_RETURN_URL = "http://192.168.1.104:8011/pay/return";

    String ENCODING = "UTF-8";
}
