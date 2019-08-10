package com.sft.simulate.service;


import com.sft.simulate.entity.Goods;
import com.sft.simulate.pojo.query.goods.GoodsQuery;
import com.sft.simulate.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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


    @Transactional(readOnly = true)
    public Page<Goods> goods(GoodsQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        Page<Goods> pages = goodsRepository.findAll((Specification<Goods>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getName())) {
                predicates.add(cb.like(root.get("name").as(String.class), "%" + query.getName() + "%"));
            }
            if(!StringUtils.isEmpty(query.getPrice())){
                predicates.add(cb.equal(root.get("price").as(String.class),query.getPrice()));
            }
            Predicate[] query1 = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(query1));
        }, pageable);
        return pages;
    }


//    @Transactional(readOnly = true)
//    public Integer findMaxId(){
//        return goodsRepository.findTopByGoodsId();
//    }

}