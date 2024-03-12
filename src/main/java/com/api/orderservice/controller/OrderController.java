package com.api.orderservice.controller;

import com.api.orderservice.dto.OrderReq;
import com.api.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderReq orderReq){
        orderService.createOrder(orderReq);
        return "Order created successfully";
    }
}
