package com.sft.simulate.service;


import com.sft.simulate.entity.Goods;
import com.sft.simulate.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
@Service
public class GoodsService{


    @Autowired
    private GoodsRepository goodsRepository;

    public Iterator<Goods> getGoodsList() {
        return goodsRepository.findAll().iterator();
    }

    public void saveGoodsList(List<Goods> goodsList) {
        goodsRepository.saveAll(goodsList);
    }

}