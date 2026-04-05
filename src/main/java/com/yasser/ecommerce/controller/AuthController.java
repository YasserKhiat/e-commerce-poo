package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.yasser.ecommerce.controller.dto.RegistrationForm;
import com.yasser.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(registrationForm);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        }
    }
}
