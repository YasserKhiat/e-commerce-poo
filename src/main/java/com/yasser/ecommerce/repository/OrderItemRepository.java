package com.yasser.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
