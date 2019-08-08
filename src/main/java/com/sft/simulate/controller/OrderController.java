package com.sft.simulate.controller;


import com.sft.simulate.entity.Trading;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.pojo.query.order.OrderQuery;
import com.sft.simulate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Response<Page<Trading>> query(OrderQuery query){
        return Response.success(orderService.orders(query));
    }


}
