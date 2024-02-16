package com.jointpurchases.domain.review.repository;

import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    ReviewEntity getById(long id);

    void deleteById(long id);

    List<ReviewEntity> getAllByProductId(long id);

}
