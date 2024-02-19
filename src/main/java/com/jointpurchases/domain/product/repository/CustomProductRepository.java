package com.jointpurchases.domain.product.repository;

import com.jointpurchases.domain.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomProductRepository {

    Optional<Product> findProductWithProductId(Long productId);
    Page<Product> findAllProduct(Pageable pageable);
}