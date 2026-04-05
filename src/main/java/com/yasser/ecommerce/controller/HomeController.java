package com.yasser.ecommerce.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yasser.ecommerce.service.CustomerOrderService;
import com.yasser.ecommerce.service.ProductService;
import com.yasser.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final UserService userService;
    private final CustomerOrderService customerOrderService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("productCount", productService.findAll().size());
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("orderCount", customerOrderService.findAll().size());
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        return userService.findByEmail(principal.getName())
                .map(user -> {
                    if (user.getRole().name().equals("ADMIN")) {
                        return "redirect:/admin";
                    }
                    model.addAttribute("currentUser", user);
                    model.addAttribute("orderCount", customerOrderService.findByUser(user).size());
                    model.addAttribute("productCount", productService.findAll().size());
                    return "user-dashboard";
                })
                .orElse("redirect:/login");
    }
}
