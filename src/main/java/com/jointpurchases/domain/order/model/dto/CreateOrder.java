package com.jointpurchases.domain.order.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class CreateOrder {

    @Getter
    public static class Request {
        private String email;
        private Long money;
        private String address;
    }

    @Getter
    @Builder
    public static class Response {
        private Long orderId;
        private String name;
        private List<Long> productIdList;
        private String address;
        private String phone;
        private LocalDateTime orderedDate;
        private String payment;
        private String type;

        public static Response fromDto(OrderDto orderDto) {
            return Response.builder()
                    .name(orderDto.getName())
                    .orderId(orderDto.getOrderId())
                    .productIdList(orderDto.getProductIdList())
                    .address(orderDto.getAddress())
                    .phone(orderDto.getPhone())
                    .orderedDate(orderDto.getOrderedDate())
                    .payment(orderDto.getPayment())
                    .type(orderDto.getType())
                    .build();
        }
    }
}
