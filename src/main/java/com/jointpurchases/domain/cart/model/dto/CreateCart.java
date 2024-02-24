package com.jointpurchases.domain.cart.model.dto;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import lombok.Builder;
import lombok.Getter;

public class CreateCart {

    @Getter
    @Builder
    public static class Response{
        private Long cartId;

        public static Response fromEntity(CartEntity cart){
            return Response.builder()
                    .cartId(cart.getCartId())
                    .build();
        }
    }
}
