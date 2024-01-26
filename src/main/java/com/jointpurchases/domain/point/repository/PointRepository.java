package com.jointpurchases.domain.point.repository;

import com.jointpurchases.domain.point.model.entity.MemberEntity;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {

    @Query("select p from point p order by p.createdDate desc limit 1")
    Optional<PointEntity> findByMemberEntity(MemberEntity memberEntity);

}
