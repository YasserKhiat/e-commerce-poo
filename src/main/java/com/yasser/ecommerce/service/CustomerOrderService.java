package com.yasser.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yasser.ecommerce.entity.CustomerOrder;
import com.yasser.ecommerce.repository.CustomerOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(Long id) {
        return customerOrderRepository.findById(id);
    }

    public CustomerOrder save(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }
}
