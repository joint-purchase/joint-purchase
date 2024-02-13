package com.jointpurchases.domain.product.repository;

import com.jointpurchases.domain.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN  p.user u " +
            "LEFT JOIN  p.category c " +
            "LEFT JOIN  p.productImages i " +
            "WHERE p.id = :productId")
    Optional<Product> findProductWithProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p  " +
            "LEFT JOIN FETCH p.user u " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.productImages i "+
            "ORDER BY p.createdAt DESC")
    Page<Product> findAllProduct(Pageable pageable);
}