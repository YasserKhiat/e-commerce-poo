package com.yasser.ecommerce.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yasser.ecommerce.entity.Product;
import com.yasser.ecommerce.service.ProductService;
import com.yasser.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/products")
    public String listProducts(Model model, Principal principal) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("currentUser", principal != null ? userService.findByEmail(principal.getName()).orElse(null) : null);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        model.addAttribute("currentUser", principal != null ? userService.findByEmail(principal.getName()).orElse(null) : null);
        return "product-details";
    }

    @GetMapping("/products/view")
    public String redirectProductsView() {
        return "redirect:/products";
    }
}
