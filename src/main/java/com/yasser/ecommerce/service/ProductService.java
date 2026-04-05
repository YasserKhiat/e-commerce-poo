package com.yasser.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.yasser.ecommerce.entity.Product;
import com.yasser.ecommerce.repository.OrderItemRepository;
import com.yasser.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String DEFAULT_IMAGE = "default-product.svg";

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getId))
                .toList();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        if (product.getImageName() == null || product.getImageName().isBlank()) {
            product.setImageName(DEFAULT_IMAGE);
        }
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));

        if (orderItemRepository.existsByProduct(product)) {
            throw new IllegalStateException("Cannot delete this product because it is already used in orders.");
        }

        productRepository.deleteById(id);
    }
}
