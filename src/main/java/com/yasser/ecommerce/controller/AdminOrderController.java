package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yasser.ecommerce.entity.enums.OrderStatus;
import com.yasser.ecommerce.service.CustomerOrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final CustomerOrderService customerOrderService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", customerOrderService.findAll());
        model.addAttribute("statuses", OrderStatus.values());
        return "admin-orders";
    }

    @GetMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam("value") OrderStatus status) {
        customerOrderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        customerOrderService.deleteById(id);
        return "redirect:/admin/orders";
    }
}
