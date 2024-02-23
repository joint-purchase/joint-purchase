package com.jointpurchases.domain.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CartItem {

    @Getter
    public static class Request {
        private Long productId;
        private Integer amount;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String productName;
        private Integer amount;
        private Integer totalPrice;

        public static Response fromDto(CartItemDto dto) {
            return Response.builder()
                    .productName(dto.getProductName())
                    .amount(dto.getAmount())
                    .totalPrice(dto.getProductTotalPrice())
                    .build();
        }
    }
}
