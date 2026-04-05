package com.yasser.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yasser.ecommerce.entity.CustomerOrder;
import com.yasser.ecommerce.entity.User;
import com.yasser.ecommerce.repository.CustomerOrderRepository;
import com.yasser.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final UserRepository userRepository;

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(Long id) {
        return customerOrderRepository.findById(id);
    }

    public CustomerOrder save(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    public CustomerOrder createSimpleOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        CustomerOrder order = CustomerOrder.builder()
                .date(LocalDate.now())
                .total(BigDecimal.ZERO)
                .user(user)
                .build();

        return customerOrderRepository.save(order);
    }

    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }
}
