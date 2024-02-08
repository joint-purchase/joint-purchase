package com.jointpurchases.domain.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CartItem {

    @Getter
    public static class Request {
        private Long productId;
        private Long amount;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String productName;
        private Long amount;
        private Long totalPrice;

        public static Response fromDto(CartItemDto dto) {
            return Response.builder()
                    .productName(dto.getProductName())
                    .amount(dto.getAmount())
                    .totalPrice(dto.getProductTotalPrice())
                    .build();
        }
    }
}
