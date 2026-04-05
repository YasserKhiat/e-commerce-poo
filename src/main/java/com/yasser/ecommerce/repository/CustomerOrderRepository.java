package com.yasser.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yasser.ecommerce.entity.CustomerOrder;
import com.yasser.ecommerce.entity.User;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findByUser(User user);

	@Query("select distinct o from CustomerOrder o left join fetch o.orderItems where o.user = :user order by o.date desc, o.id desc")
	List<CustomerOrder> findByUserWithItems(@Param("user") User user);
}
