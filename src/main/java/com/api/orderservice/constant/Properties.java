package com.api.orderservice.constant;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Properties{
    @Value("${inventory.host}")
    private String inventoryHost;
}
