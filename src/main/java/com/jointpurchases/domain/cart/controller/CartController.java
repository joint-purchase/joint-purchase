package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.cart.model.dto.CreateCart;
import com.jointpurchases.domain.cart.model.dto.DeleteCart;
import com.jointpurchases.domain.cart.service.CartService;
import com.jointpurchases.global.tool.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니 생성
    @PostMapping
    public CreateCart.Response createCart(@LoginUser User user) {
        return this.cartService.createCart(user);
    }

    //장바구니 삭제
    @DeleteMapping
    public DeleteCart.Response deleteCart(@LoginUser User user) {
        return this.cartService.deleteCart(user);
    }

}
