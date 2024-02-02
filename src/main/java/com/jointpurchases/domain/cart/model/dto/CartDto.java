package com.jointpurchases.domain.cart.model.dto;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long cartId;
    private Long memberId;
    private Long productId;
    private String productName;
    private Long amount;
    private Long totalPrice;

    public static CartDto fromEntity(CartEntity cartEntity) {
        return CartDto.builder()
                .cartId(cartEntity.getCartId())
                .memberId(cartEntity.getMemberEntity().getId())
                .productId(cartEntity.getProductEntity().getProductId())
                .amount(cartEntity.getAmount())
                .totalPrice(cartEntity.getTotalPrice())
                .productName(cartEntity.getProductEntity().getProductName())
                .build();
    }
}
