package com.jointpurchases.domain.order.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.order.model.dto.CancelOrder;
import com.jointpurchases.domain.order.model.dto.CreateOrder;
import com.jointpurchases.domain.order.service.OrderService;
import com.jointpurchases.global.tool.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //상품 주문
    @PostMapping
    public CreateOrder.Response createOrder(@RequestBody CreateOrder.Request request,
                                            @LoginUser User user) {

        return CreateOrder.Response.fromDto(
                this.orderService.createOrder(user, request.getMoney(),
                        request.getAddress()));
    }

    //상품 주문 취소
    @PutMapping
    public CancelOrder.Response cancelOrder(@RequestBody CancelOrder.Request request,
                                            @LoginUser User user) {
        return this.orderService.cancelOrder(user, request.getOrderId());
    }
}
