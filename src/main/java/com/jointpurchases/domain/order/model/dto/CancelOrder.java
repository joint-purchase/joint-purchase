package com.jointpurchases.domain.order.model.dto;

import com.jointpurchases.domain.order.model.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;

public class CancelOrder {

    @Getter
    public static class Request {
        private Long orderId;
    }

    @Getter
    @Builder
    public static class Response {
        private String email;
        private Long orderId;
        private Integer money;
        private String payment;

        public static Response from(OrderEntity order, Integer money) {
            return Response.builder()
                    .email(order.getUserEntity().getEmail())
                    .orderId(order.getOrderId())
                    .payment(order.getPayment())
                    .money(money)
                    .build();
        }
    }
}
