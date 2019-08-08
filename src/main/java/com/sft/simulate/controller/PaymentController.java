package com.sft.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.sft.simulate.entity.Goods;
import com.sft.simulate.entity.Member;
import com.sft.simulate.enums.common.ResponseEnum;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.service.GoodsService;
import com.sft.simulate.service.MemberService;
import com.sft.simulate.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

import static com.sft.simulate.pojo.CurrencyConstant.ENCODING;
import static com.sft.simulate.pojo.CurrencyConstant.PAYMENT_ORDER_URL;

@RestController
@RequestMapping("pay")
@Slf4j
public class PaymentController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("order")
    public Response paymentOrder(Integer payType,String money,int goodsnum){
        String payHtml = null;
        try {
            //1.随机选取订单会员
            Member member = randomMember();
            //2.选取一个与其价格相同的商品
            Goods goods = randomGoods(money);
            if(goods == null){
                return Response.fail("无该价位商品");
            }
            //3.进行下单
            Map<String,String> map = fillParams(member,goods,payType,goodsnum);
            String result = HttpClientUtil.post(PAYMENT_ORDER_URL,map,ENCODING);
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


    private Map<String,String> fillParams(Member member,Goods goods,int payType,int goodsnum){
        Map<String,String> map = new HashMap<>();
        map.put("memberId",String.valueOf(member.getId()));
        map.put("goodsId",String.valueOf(goods.getId()));
        map.put("goodsNum",String.valueOf(goodsnum));
        map.put("paymentId",String.valueOf(payType));
        map.put("shippingId","1");
        return map;
    }

    public Map<BigDecimal, List<Goods>> getAllGoods(){
        Map<BigDecimal,List<Goods>> map = new HashMap<>();
        List<Goods> goodsList = copyIterator(goodsService.getGoodsList());
        for (Goods goods : goodsList){
            if(map.get(goods.getPrice())!=null){
                map.get(goods.getPrice()).add(goods);
            }else{
                List<Goods> gs = new ArrayList<>();
                gs.add(goods);
                map.put(goods.getPrice(),gs);
            }
        }
        return map;
    }


    private Goods randomGoods(String money){
        BigDecimal mon = new BigDecimal(money);
        Map<BigDecimal,List<Goods>> map = this.getAllGoods();
        if(map.get(mon)==null){
            return null;
        }else if(map.get(mon).size()==0){
            return map.get(mon).get(0);
        }else{
            Random random = new Random();
            int num = random.nextInt(map.get(mon).size());
            return map.get(mon).get(num);
        }
    }


    private Member randomMember(){
        List<Member> members = copyIterator(memberService.getMemberList());
        Random random = new Random();
        int num = random.nextInt(members.size());
        return members.get(num);
    }


    static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }


}
