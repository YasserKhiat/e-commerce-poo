package com.yasser.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yasser.ecommerce.controller.dto.RegistrationForm;
import com.yasser.ecommerce.entity.User;
import com.yasser.ecommerce.entity.enums.Role;
import com.yasser.ecommerce.repository.CustomerOrderRepository;
import com.yasser.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    public User register(RegistrationForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = User.builder()
                .name(form.getName())
                .email(form.getEmail())
                .address(form.getAddress())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public void createDefaultAdminIfMissing() {
        if (userRepository.existsByEmail("admin@shop.com")) {
            return;
        }

        User admin = User.builder()
                .name("Administrator")
                .email("admin@shop.com")
                .address("Head Office")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
    }

    public User save(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found."));
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(existingUser.getPassword());
            } else if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (customerOrderRepository.existsByUser(user)) {
            throw new IllegalStateException("Cannot delete this user because they already have orders.");
        }

        userRepository.deleteById(id);
    }
}
