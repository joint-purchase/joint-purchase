package com.jointpurchases.domain.cart.model.entity;

import com.jointpurchases.domain.auth.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userEntity;
    private Integer totalPrice;
}
