package com.yasser.ecommerce.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderForm {

    @NotNull(message = "Please select a user")
    private Long userId;
}
