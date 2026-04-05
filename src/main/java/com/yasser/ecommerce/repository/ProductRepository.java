package com.yasser.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
