package com.jointpurchases.domain.order.model.dto;

import com.jointpurchases.domain.order.model.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDto {
    private Long orderId;
    private String email;
    private String name;
    private List<Long> productIdList;
    private String address;
    private String phone;
    private LocalDateTime orderedDate;
    private String payment;
    private String type;

    public static OrderDto from(List<Long> productIdList, String address, OrderEntity order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .email(order.getMemberEntity().getEmail())
                .name(order.getMemberEntity().getUsername())
                .address(address)
                .phone(order.getMemberEntity().getPhone())
                .orderedDate(LocalDateTime.now())
                .productIdList(productIdList)
                .payment(order.getPayment())
                .type(order.getType())
                .build();
    }
}
