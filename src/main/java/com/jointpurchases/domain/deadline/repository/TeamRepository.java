package com.jointpurchases.domain.deadline.repository;

import com.jointpurchases.domain.deadline.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    Optional<TeamEntity> findByDeadlineId(long id);
}
