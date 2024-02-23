package com.jointpurchases.domain.cart.model.entity;

import com.jointpurchases.domain.product.model.entity.Product;
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
    private Product product;

    private Integer amount;

    private Integer productTotalPrice;

    public void increaseAmount() {
        this.amount += 1;
        this.productTotalPrice += product.getPrice();
    }

    public void decreaseAmount() {
        this.amount -= 1;
        this.productTotalPrice -= product.getPrice();
    }
}
