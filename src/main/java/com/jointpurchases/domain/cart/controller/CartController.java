package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.Cart;
import com.jointpurchases.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니에 상품 추가
    @PostMapping("/cart/products")
    public Cart.Response addProductToCart(@RequestBody Cart.Request product,
                                          @RequestBody String email) {
        return Cart.Response.fromDto(this.cartService.addProductToCart(
                product.getProductId(), product.getAmount(), email));
    }


}
