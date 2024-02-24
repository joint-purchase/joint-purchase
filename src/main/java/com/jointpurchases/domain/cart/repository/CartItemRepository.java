package com.jointpurchases.domain.cart.repository;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartEntityAndProduct(CartEntity cartEntity, Product product);

    List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

    boolean existsByCartEntityAndProduct(CartEntity cartEntity, Product product);
}
