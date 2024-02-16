package com.jointpurchases.domain.cart.repository;

import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductId(Long productId);

    List<ProductEntity> findAllByProductIdIn(List<Long> productId);
}
