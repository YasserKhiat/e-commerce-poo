package com.yasser.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
