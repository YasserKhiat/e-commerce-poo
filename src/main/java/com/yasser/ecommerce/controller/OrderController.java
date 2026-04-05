package com.yasser.ecommerce.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yasser.ecommerce.controller.form.OrderItemForm;
import com.yasser.ecommerce.controller.form.OrderForm;
import com.yasser.ecommerce.service.CustomerOrderService;
import com.yasser.ecommerce.service.ProductService;
import com.yasser.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final CustomerOrderService customerOrderService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/orders/new")
    public String showCreateForm(Model model) {
        OrderForm orderForm = new OrderForm();
        productService.findAll().forEach(product -> {
            OrderItemForm item = new OrderItemForm();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setAvailableStock(product.getStock());
            item.setQuantity(0);
            orderForm.getItems().add(item);
        });

        model.addAttribute("orderForm", orderForm);
        return "order-form";
    }

    @PostMapping("/orders/save")
    public String saveOrder(@Valid @ModelAttribute("orderForm") OrderForm orderForm,
                            BindingResult bindingResult,
                            Model model,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "order-form";
        }

        try {
            customerOrderService.createOrderForUser(orderForm, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Order created successfully.");
            return "redirect:/my-orders";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "order-form";
        }
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model, Principal principal) {
        var user = userService.getByEmailOrThrow(principal.getName());
        model.addAttribute("orders", customerOrderService.findByUser(user));
        return "my-orders";
    }
}
