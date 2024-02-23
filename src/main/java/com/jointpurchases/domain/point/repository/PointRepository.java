package com.jointpurchases.domain.point.repository;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {

    @Query("select p from point p where p.userEntity = :memberEntity order by p.createdDate DESC")
    Page<PointEntity> findByUserEntity(User userEntity, Pageable pageable);

    List<PointEntity> findAllByUserEntityAndCreatedDateBetween
            (User userEntity, LocalDateTime startDateTime, LocalDateTime endDate);
}
