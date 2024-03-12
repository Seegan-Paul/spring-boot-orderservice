package com.api.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDto {
    @JsonProperty("SkuCode")
    private String skuCode;
    @JsonProperty("Price")
    private BigDecimal price;
    @JsonProperty("Quantity")
    private Integer quantity;
}
