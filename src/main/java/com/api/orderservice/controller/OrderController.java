package com.api.orderservice.controller;

import com.api.orderservice.dto.OrderReq;
import com.api.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderReq orderReq){
        log.info("Starting create order service");
        orderService.createOrder(orderReq);
        log.info("Finished create order service");
        return "Order created successfully";
    }
}
