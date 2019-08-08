package com.sft.simulate.service;

import com.sft.simulate.entity.Order;
import com.sft.simulate.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

}
