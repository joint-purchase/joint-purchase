package com.jointpurchases.domain.cart.model.dto;

import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private Long cartId;
    private Long productId;
    private String productName;
    private Long amount;
    private Long productTotalPrice;

    public static CartItemDto fromEntity(CartItemEntity cartItemEntity) {
        return CartItemDto.builder()
                .cartId(cartItemEntity.getCartEntity().getCartId())
                .productId(cartItemEntity.getProductEntity().getProductId())
                .amount(cartItemEntity.getAmount())
                .productTotalPrice(cartItemEntity.getProductTotalPrice())
                .productName(cartItemEntity.getProductEntity().getProductName())
                .build();
    }
}
