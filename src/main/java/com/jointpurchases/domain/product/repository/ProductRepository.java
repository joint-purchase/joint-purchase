package com.jointpurchases.domain.product.repository;

import com.jointpurchases.domain.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

}