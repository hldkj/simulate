package com.sft.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.sft.simulate.entity.Goods;
import com.sft.simulate.entity.Member;
import com.sft.simulate.entity.Trading;
import com.sft.simulate.enums.common.ResponseEnum;
import com.sft.simulate.enums.payment.PayStatusEnum;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.service.GoodsService;
import com.sft.simulate.service.MemberService;
import com.sft.simulate.service.OrderService;
import com.sft.simulate.utils.HttpClientUtil;
import com.sft.simulate.utils.OrderNoCreateUtil;
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
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public String paymentOrder(Integer payType,String money,int goodsnum){
        String payHtml = null;
        try {
            //1.随机选取订单会员
            Member member = randomMember();
            //2.选取一个与其价格相同的商品
            Goods goods = randomGoods(money);
            if(goods == null){
                return "金额不匹配";
            }
            //3.创建订单
            createOrder(member,goods);
            //4.进行下单
            Map<String,String> map = fillParams(member,goods,payType,goodsnum);
            String result = HttpClientUtil.post(PAYMENT_ORDER_URL,map,ENCODING);
            Response response = JSONObject.parseObject(result,Response.class);
            if(response.getCode()!= ResponseEnum.SUCCESS.getCode()){
                return response.getMsg();
            }
            payHtml = response.getData().toString();
        } catch (Exception e) {
            log.error("系统异常:{}",e);
//            return Response.fail("系统异常");
        }
        return payHtml;
    }


    /**
     * 创建订单
     * @param member
     * @param goods
     */
    private void createOrder(Member member,Goods goods){
        Trading order = new Trading();
        order.setOrderNum(OrderNoCreateUtil.getOrderNo());
        order.setUsername(member.getName());
        order.setGoodsname(goods.getName());
        order.setPrice(goods.getPrice());
        order.setCreateTime(new Date());
        order.setStatus(PayStatusEnum.UNPAID.getCode());
        order.setMobile(member.getMobile());
        orderService.saveOrder(order);
    }


    /**
     * 填充请求数据
     * @param member
     * @param goods
     * @param payType
     * @param goodsnum
     * @return
     */
    private Map<String,String> fillParams(Member member,Goods goods,int payType,int goodsnum){
        Map<String,String> map = new HashMap<>();
        map.put("memberId",String.valueOf(member.getMemberId()));
        map.put("goodsId",String.valueOf(goods.getGoodsId()));
        map.put("goodsNum",String.valueOf(goodsnum));
        map.put("paymentId",String.valueOf(payType));
        map.put("shippingId","1");
        return map;
    }

    /**
     * 获取所有商品
     * @return
     */
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


    /**
     * 进行商品筛选
     * @param money
     * @return
     */
    private Goods randomGoods(String money){
        BigDecimal mon = new BigDecimal(money);
        mon.setScale(2);
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

    /**
     * 进行会员筛选
     * @return
     */
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
