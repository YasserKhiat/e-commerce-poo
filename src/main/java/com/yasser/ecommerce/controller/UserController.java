package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yasser.ecommerce.entity.User;
import com.yasser.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("formTitle", "Add User");
        model.addAttribute("formAction", "/users/save");
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", user.getId() == null ? "Add User" : "Edit User");
            model.addAttribute("formAction", user.getId() == null ? "/users/save" : "/users/update/" + user.getId());
            return "user-form";
        }

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        model.addAttribute("formTitle", "Edit User");
        model.addAttribute("formAction", "/users/update/" + user.getId());
        return "user-form";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Edit User");
            model.addAttribute("formAction", "/users/update/" + id);
            return "user-form";
        }

        user.setId(id);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
