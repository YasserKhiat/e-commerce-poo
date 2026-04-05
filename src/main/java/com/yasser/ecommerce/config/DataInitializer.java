package com.yasser.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yasser.ecommerce.service.UserService;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedDefaultAdmin(UserService userService) {
        return args -> userService.createDefaultAdminIfMissing();
    }
}
