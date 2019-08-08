package com.sft.simulate.service;

import com.sft.simulate.entity.Trading;
import com.sft.simulate.pojo.query.order.OrderQuery;
import com.sft.simulate.repository.OrderRepository;
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
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Trading order){
        orderRepository.save(order);
    }


    @Transactional(readOnly = true)
    public Page<Trading> orders(OrderQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        Page<Trading> pages = orderRepository.findAll((Specification<Trading>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getUsername())) {
                predicates.add(cb.like(root.get("username").as(String.class), "%" + query.getUsername() + "%"));
            }
            if (!StringUtils.isEmpty(query.getGoodsname())) {
                predicates.add(cb.like(root.get("goodsname").as(String.class), "%" + query.getGoodsname() + "%"));
            }
            if (!StringUtils.isEmpty(query.getOrderNum())) {
                predicates.add(cb.equal(root.get("orderNum").as(String.class), query.getOrderNum()));
            }
            Predicate[] query1 = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(query1));
        }, pageable);
        return pages;
    }

}
