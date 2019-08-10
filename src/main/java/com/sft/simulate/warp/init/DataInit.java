package com.sft.simulate.warp.init;

import com.sft.simulate.entity.Goods;
import com.sft.simulate.entity.Member;
import com.sft.simulate.service.GoodsService;
import com.sft.simulate.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
public class DataInit {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MemberService memberService;

    public static Map<BigDecimal, List<Goods>> goods = new HashMap<>();

    public static List<Member> members = new ArrayList<>();

    @PostConstruct
    public void init(){
        log.info("系统启动，加载goodsMap");
        goods = initGoods();
        members = initMember();
    }


    public List<Member> initMember(){
        return copyIterator(memberService.getMemberList());
    }

    public Map<BigDecimal,List<Goods>> initGoods(){
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


    static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }


}
