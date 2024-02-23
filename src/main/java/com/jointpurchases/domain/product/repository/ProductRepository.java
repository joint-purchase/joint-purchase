package com.jointpurchases.domain.product.repository;

import com.jointpurchases.domain.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    List<Product> findAllByIdIn(List<Long> productId);
}