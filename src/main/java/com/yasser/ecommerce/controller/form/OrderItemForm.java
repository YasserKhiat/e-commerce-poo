package com.yasser.ecommerce.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemForm {

    @NotNull
    private Long productId;

    private String productName;

    private Integer availableStock;

    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity = 0;
}
