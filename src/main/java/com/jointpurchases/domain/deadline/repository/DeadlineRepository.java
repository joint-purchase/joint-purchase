package com.jointpurchases.domain.deadline.repository;

import com.jointpurchases.domain.deadline.model.entity.DeadlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadlineRepository extends JpaRepository<DeadlineEntity, Long> {
}
