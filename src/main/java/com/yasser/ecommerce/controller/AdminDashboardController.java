package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yasser.ecommerce.service.CustomerOrderService;
import com.yasser.ecommerce.service.ProductService;
import com.yasser.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ProductService productService;
    private final UserService userService;
    private final CustomerOrderService customerOrderService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.findAll().size());
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("orderCount", customerOrderService.findAll().size());
        return "admin-dashboard";
    }
}
