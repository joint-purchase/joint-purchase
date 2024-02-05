package com.jointpurchases.domain.review.repository;

import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Integer> {
}
