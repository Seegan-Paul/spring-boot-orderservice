package com.api.orderservice.service;

import com.api.orderservice.dto.OrderLineItemsDto;
import com.api.orderservice.dto.OrderReq;
import com.api.orderservice.model.OrderLineItems;
import com.api.orderservice.model.Orders;
import com.api.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderReq orderReq){
        Orders orders = new Orders();
        orders.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderReq.getOrderLineItemsDto().stream().map(this::map).toList();
        orders.setOrderLineItems(orderLineItems);
        log.info("saving the data in database");
        orderRepository.save(orders);
    }

    private OrderLineItems map(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

}
