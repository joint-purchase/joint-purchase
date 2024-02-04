package com.jointpurchases.domain.product.repository;

import com.jointpurchases.domain.product.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}