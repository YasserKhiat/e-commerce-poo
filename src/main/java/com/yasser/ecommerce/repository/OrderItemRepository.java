package com.yasser.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.OrderItem;
import com.yasser.ecommerce.entity.Product;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	boolean existsByProduct(Product product);
}
