package com.jointpurchases.domain.cart.repository;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartEntityAndProductEntity(CartEntity cartEntity, ProductEntity productEntity);

    List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

    boolean existsByCartEntityAndProductEntity(CartEntity cartEntity, ProductEntity productEntity);
}
