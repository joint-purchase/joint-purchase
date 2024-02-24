package com.jointpurchases.domain.order.repository;

import com.jointpurchases.domain.order.model.entity.OrderedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItemEntity, Long> {
}
