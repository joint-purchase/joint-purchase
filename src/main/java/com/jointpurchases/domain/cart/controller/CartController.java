package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.Cart;
import com.jointpurchases.domain.cart.model.dto.GetCartList;
import com.jointpurchases.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니에 상품 추가
    @PostMapping("/products")
    public Cart.Response addProductToCart(@RequestBody Cart.Request product) {
        return Cart.Response.fromDto(this.cartService.addProductToCart(
                product.getProductId(), product.getAmount(), product.getEmail()));
    }

    //장바구니 조회
    @GetMapping
    public GetCartList getCartList(@RequestParam String email) {
        List<Cart.Response> cartList = this.cartService.getCartList(email);

        //cartList totalPrice를 더해서 계산 해야 하는 총액을 계산
        Long payTotalPrice = cartList.stream()
                .mapToLong(Cart.Response::getTotalPrice).sum();

        return new GetCartList(payTotalPrice, cartList);
    }

    //장바구니 상품 수량 증가
    @PutMapping("/{cartId}/increase")
    public Cart.Response updateCartAmountIncrease(@PathVariable Long cartId,
                                                  @RequestParam String email) {
        return Cart.Response.fromDto(this.cartService
                .updateCartAmountIncrease(cartId, email));
    }

    //장바구니 상품 수량 감소
    @PutMapping("/{cartId}/decrease")
    public Cart.Response updateCartAmountDecrease(@PathVariable Long cartId,
                                                  @RequestParam String email) {
        return Cart.Response.fromDto(this.cartService
                .updateCartAmountDecrease(cartId, email));
    }

    //장바구니 상품 삭제
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> removeCartProduct(@PathVariable Long cartId,
                                                    @RequestParam String email){
        this.cartService.removeCartProduct(cartId, email);

        return ResponseEntity.ok("장바구니의 상품이 삭제되었습니다.");
    }


}
