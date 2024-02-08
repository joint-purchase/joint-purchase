package com.jointpurchases.domain.cart.repository;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findAllByMemberEntity(MemberEntity memberEntity);

    Optional<CartEntity> findByMemberEntity(MemberEntity memberEntity);

    boolean existsByProductEntity(ProductEntity productEntity);

    Optional<CartEntity> findByCartId(Long cartId);

    @Transactional
    void deleteByCartId(Long cartId);
}
