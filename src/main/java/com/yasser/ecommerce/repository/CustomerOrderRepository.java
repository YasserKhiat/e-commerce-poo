package com.yasser.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasser.ecommerce.entity.CustomerOrder;
import com.yasser.ecommerce.entity.User;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findByUser(User user);
}
