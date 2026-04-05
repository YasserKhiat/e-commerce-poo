package com.yasser.ecommerce.controller.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrderForm {

    private List<OrderItemForm> items = new ArrayList<>();
}
