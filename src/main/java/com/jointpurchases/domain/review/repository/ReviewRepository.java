package com.jointpurchases.domain.review.repository;

import com.jointpurchases.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review getOneById(int id);
    void deleteById(int id);
}
