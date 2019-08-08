package com.sft.simulate.service;

import com.sft.simulate.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class SingleObject {

    @Autowired
    private GoodsService goodsService;

    private static SingleObject instance = new SingleObject();

    private SingleObject(){}

    public static SingleObject getInstance(){
        return instance;
    }

    public static Map<BigDecimal, List<Goods>> goodsMap;

    /*public Map<BigDecimal, List<Goods>> getAllGoods(){
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
    }*/

    static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }

}
