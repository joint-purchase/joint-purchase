package com.jointpurchases.domain.review.repository;

import com.jointpurchases.domain.review.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
}
