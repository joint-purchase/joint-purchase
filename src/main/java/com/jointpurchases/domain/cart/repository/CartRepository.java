package com.jointpurchases.domain.cart.repository;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserEntity(User userEntity);

    boolean existsByUserEntity(User userEntity);

    @Transactional
    void deleteByUserEntity(User userEntity);

}
