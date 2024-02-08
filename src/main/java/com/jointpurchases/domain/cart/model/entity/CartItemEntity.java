package com.jointpurchases.domain.cart.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "cart_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private Long amount;

    private Long productTotalPrice;

    public void increaseAmount() {
        this.amount += 1;
        this.productTotalPrice += productEntity.getPrice();
    }

    public void decreaseAmount() {
        this.amount -= 1;
        this.productTotalPrice -= productEntity.getPrice();
    }
}
