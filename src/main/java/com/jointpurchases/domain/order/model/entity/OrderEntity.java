package com.jointpurchases.domain.order.model.entity;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "order")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "cartId")
    private CartEntity cartEntity;

    private String payment;

    private LocalDateTime orderedDate;
}
