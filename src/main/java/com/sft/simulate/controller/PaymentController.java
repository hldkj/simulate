package com.sft.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.sft.simulate.entity.Goods;
import com.sft.simulate.entity.Member;
import com.sft.simulate.entity.Trading;
import com.sft.simulate.enums.common.ResponseEnum;
import com.sft.simulate.enums.payment.PayStatusEnum;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.service.OrderService;
import com.sft.simulate.utils.HttpClientUtil;
import com.sft.simulate.utils.OrderNoCreateUtil;
import com.sft.simulate.warp.init.DataInit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

import static com.sft.simulate.pojo.CurrencyConstant.*;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Value("${host.b2c}")
    private String HOST;

    @Value("${host.local}")
    private String LOCAL;

    private String mchId = "12342546565";

    private String secret = "e35e952f138b40c2b907f2552d1b48af";

    @PostMapping("/order")
    public Response<String> paymentOrder(Integer payType, String amount,String callBackUrl,String returnUrl,String outOrderNo,String sign){
        String payHtml = null;
        try {
            String checkSign = DigestUtils.md5Hex(mchId+outOrderNo+amount+callBackUrl+secret);
            if(!checkSign.equals(sign)){
                return Response.fail("签名异常");
            }
            //1.随机选取订单会员
            Member member = randomMember();
            //2.选取一个与其价格相同的商品
            Goods goods = randomGoods(amount);
            if(goods == null){
                return Response.fail("金额不支持");
            }
            //3.创建订单
            Trading order = createOrder(member,goods,1,payType,outOrderNo,callBackUrl);
            //4.进行下单
            Map<String,String> map = fillParams(member,goods,order,returnUrl);
            String result = HttpClientUtil.post(HOST+PAYMENT_ORDER_URL,map,ENCODING);
            Response response = JSONObject.parseObject(result,Response.class);
            if(response.getCode()!= ResponseEnum.SUCCESS.getCode()){
                return Response.fail(response.getMsg());
            }
            payHtml = response.getData().toString();
        } catch (Exception e) {
            log.error("系统异常:{}",e);
            return Response.fail("系统异常");
        }
        return Response.success(payHtml);
    }

    @PostMapping("/callback")
    public String callback(String orderNum){
        Trading trading = orderService.findByOrderNum(orderNum);
        if(trading.getStatus()==PayStatusEnum.UNPAID.getCode()){
            Map<String,String> map = new HashMap<>();
            map.put("mchId",mchId);
            map.put("outOrderNo",trading.getOutOrderNo());
            map.put("status",String.valueOf(PayStatusEnum.SUCCESS.getCode()));
            map.put("amount",String.valueOf(trading.getPrice().intValue()));
            map.put("sign", DigestUtils.md5Hex(mchId+trading.getOutOrderNo()+map.get("amount")+map.get("status")+secret));
            String response = HttpClientUtil.post(trading.getCallbackUrl(),map,"UTF-8");
            if("success".equals(response)){
                 orderService.updateStatusByOrderNum(PayStatusEnum.SUCCESS.getCode(),orderNum);
                 return "success";
            }
            return "fail";
        }
        return "success";
    }


    /**
     * 创建订单
     * @param member
     * @param goods
     */
    private Trading createOrder(Member member,Goods goods,Integer goodsNum,Integer payType,String outOrderNo,String callBackUrl){
        Trading order = new Trading();
        order.setOrderNum(OrderNoCreateUtil.getOrderNo());
        order.setUsername(member.getName());
        order.setGoodsname(goods.getName());
        order.setPrice(goods.getPrice());
        order.setCallbackUrl(callBackUrl);
        order.setOutOrderNo(outOrderNo);
        order.setCreateTime(new Date());
        order.setStatus(PayStatusEnum.UNPAID.getCode());
        order.setMobile(member.getMobile());
        order.setPayType(payType);
        order.setGoodsNum(goodsNum);
        orderService.saveOrder(order);
        return order;
    }


    /**
     * 填充请求数据
     * @param member
     * @param goods
     * @return
     */
    private Map<String,String> fillParams(Member member, Goods goods, Trading order,String returnUrl){
        Map<String,String> map = new HashMap<>();
        map.put("memberId",String.valueOf(member.getMemberId()));
        map.put("goodsId",String.valueOf(goods.getGoodsId()));
        map.put("goodsNum",String.valueOf(order.getGoodsNum()));
        map.put("paymentId",String.valueOf(order.getPayType()));
        map.put("callBackUrl", LOCAL+PAYMENT_CALLBACK_URL);
        map.put("returnUrl",returnUrl);
        map.put("orderNum",order.getOrderNum());
        map.put("shippingId","1");
        return map;
    }

    /**
     * 进行商品筛选
     * @param money
     * @return
     */
    private Goods randomGoods(String money){
        BigDecimal mon = new BigDecimal(money).setScale(2);
        //Map<BigDecimal,List<Goods>> map = this.getAllGoods();
        Map<BigDecimal,List<Goods>> map = DataInit.goods;
        if(map.get(mon)==null){
            return null;
        }else if(map.get(mon).size()==1){
            return map.get(mon).get(0);
        }else{
            Random random = new Random();
            int num = random.nextInt(map.get(mon).size());
            return map.get(mon).get(num);
        }
    }

    /**
     * 进行会员筛选
     * @return
     */
    private Member randomMember(){
        List<Member> members = DataInit.members;
        Random random = new Random();
        int num = random.nextInt(members.size());
        return members.get(num);
    }
}
