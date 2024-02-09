package com.jointpurchases.domain.security.repository;

import com.jointpurchases.domain.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByName(String name);
    Optional<UserEntity> findByName(String name);
}
