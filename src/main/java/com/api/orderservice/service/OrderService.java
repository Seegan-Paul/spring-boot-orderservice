package com.api.orderservice.service;

import com.api.orderservice.constant.Properties;
import com.api.orderservice.dto.OrderLineItemsDto;
import com.api.orderservice.dto.OrderReq;
import com.api.orderservice.model.InventoryResponse;
import com.api.orderservice.model.OrderLineItems;
import com.api.orderservice.model.Orders;
import com.api.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.api.orderservice.constant.Constant.INVENTORY_URL;

@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Properties properties;

    @Autowired
    private WebClient webClient;

    public void createOrder(OrderReq orderReq){
        Orders orders = new Orders();
        orders.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderReq.getOrderLineItemsDto().stream().map(this::map).toList();
        orders.setOrderLineItems(orderLineItems);

        List<String> skuCodes = orders.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        log.info("calling inventory service");
        InventoryResponse[] inventoryResponses = webClient.get()
                        .uri( properties.getInventoryHost() + INVENTORY_URL, uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResponse[].class).block();
        log.info("retrieved data from inventory service {}", (Object) inventoryResponses);

        assert inventoryResponses != null;
        boolean result = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if(result){
            log.info("saving the data in database");
            orderRepository.save(orders);
        }else {
            throw new IllegalArgumentException("product stock is not available in inventory");
        }

    }

    private OrderLineItems map(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

}
