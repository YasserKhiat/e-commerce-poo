package com.yasser.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yasser.ecommerce.controller.form.OrderForm;
import com.yasser.ecommerce.controller.form.OrderItemForm;
import com.yasser.ecommerce.entity.CustomerOrder;
import com.yasser.ecommerce.entity.OrderItem;
import com.yasser.ecommerce.entity.Product;
import com.yasser.ecommerce.entity.User;
import com.yasser.ecommerce.repository.CustomerOrderRepository;
import com.yasser.ecommerce.repository.ProductRepository;
import com.yasser.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(Long id) {
        return customerOrderRepository.findById(id);
    }

    public CustomerOrder save(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Transactional
    public CustomerOrder createOrder(OrderForm orderForm) {
        User user = userRepository.findById(orderForm.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Selected user does not exist."));

        CustomerOrder order = CustomerOrder.builder()
                .date(LocalDate.now())
                .total(BigDecimal.ZERO)
                .user(user)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        boolean hasAtLeastOneItem = false;

        for (OrderItemForm itemForm : orderForm.getItems()) {
            Integer quantity = itemForm.getQuantity() == null ? 0 : itemForm.getQuantity();
            if (quantity <= 0) {
                continue;
            }

            Product product = productRepository.findById(itemForm.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found."));

            if (product.getStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .customerOrder(order)
                    .build();

            order.getOrderItems().add(orderItem);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            hasAtLeastOneItem = true;
        }

        if (!hasAtLeastOneItem) {
            throw new IllegalArgumentException("Please add at least one product quantity greater than zero.");
        }

        order.setTotal(total);
        return customerOrderRepository.save(order);
    }

    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }
}
