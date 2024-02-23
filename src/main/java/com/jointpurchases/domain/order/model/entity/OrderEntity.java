package com.jointpurchases.domain.order.model.entity;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "order_table")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User userEntity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    private String payment;

    private LocalDateTime orderedDate;

    private String address;

    private String type;

    public void cancelOrder() {
        this.payment = "주문 취소";
    }
}
