package com.jointpurchases.domain.review.repository;

import com.jointpurchases.domain.review.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity getById(long id);
}
