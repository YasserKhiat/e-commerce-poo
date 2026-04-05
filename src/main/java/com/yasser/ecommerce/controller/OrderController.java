package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yasser.ecommerce.controller.form.OrderForm;
import com.yasser.ecommerce.service.CustomerOrderService;
import com.yasser.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CustomerOrderService customerOrderService;
    private final UserService userService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", customerOrderService.findAll());
        return "orders";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("users", userService.findAll());
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(@Valid @ModelAttribute("orderForm") OrderForm orderForm,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "order-form";
        }

        customerOrderService.createSimpleOrder(orderForm.getUserId());
        redirectAttributes.addFlashAttribute("successMessage", "Order created successfully.");
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        customerOrderService.deleteById(id);
        return "redirect:/orders";
    }
}
