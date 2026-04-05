package com.yasser.ecommerce.controller.form;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderForm {

    @NotNull(message = "Please select a user")
    private Long userId;

    private List<OrderItemForm> items = new ArrayList<>();
}
