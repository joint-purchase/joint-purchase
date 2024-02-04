package com.jointpurchases.domain.cart.model.entity;

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
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private Long amount;

    private Long totalPrice;

    public void increaseAmount() {
        this.amount += 1;
        this.totalPrice += productEntity.getPrice();
    }

    public void decreaseAmount() {
        this.amount -= 1;
        this.totalPrice -= productEntity.getPrice();
    }

}
