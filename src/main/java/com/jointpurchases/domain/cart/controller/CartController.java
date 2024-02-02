package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.Cart;
import com.jointpurchases.domain.cart.model.dto.GetCartList;
import com.jointpurchases.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //장바구니 조회
    @GetMapping("/cart")
    public GetCartList getCartList(@RequestParam String email) {
        List<Cart.Response> cartList = this.cartService.getCartList(email);

        //cartList totalPrice를 더해서 계산 해야 하는 총액을 계산
        Long payTotalPrice = cartList.stream()
                .mapToLong(Cart.Response::getTotalPrice).sum();

        return new GetCartList(payTotalPrice, cartList);
    }


}
