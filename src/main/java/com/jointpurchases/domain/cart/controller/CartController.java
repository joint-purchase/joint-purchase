package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.CreateCart;
import com.jointpurchases.domain.cart.model.dto.DeleteCart;
import com.jointpurchases.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니 생성
    @PostMapping
    public CreateCart.Response createCart(@RequestBody CreateCart.Request request) {
        return this.cartService.createCart(request.getEmail());
    }

    //장바구니 삭제
    @DeleteMapping
    public DeleteCart.Response deleteCart(@RequestBody DeleteCart.Request request) {
        return this.cartService.deleteCart(request.getEmail());
    }

}
