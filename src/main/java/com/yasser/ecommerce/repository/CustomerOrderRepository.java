package com.yasser.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
