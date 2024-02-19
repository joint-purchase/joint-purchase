package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.CartItem;
import com.jointpurchases.domain.cart.model.dto.GetCartItemList;
import com.jointpurchases.domain.cart.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    //장바구니에 상품 추가
    @PostMapping("/products")
    public CartItem.Response addProductToCart(@RequestBody CartItem.Request product) {
        return CartItem.Response.fromDto(this.cartItemService.addProductToCart(
                product.getProductId(), product.getAmount(), product.getEmail()));
    }

    //장바구니 조회
    @GetMapping
    public GetCartItemList getCartItemList(@RequestParam String email) {
        List<CartItem.Response> cartItemList = this.cartItemService.getCartItemList(email);

        //cartList totalPrice를 더해서 계산 해야 하는 총액을 계산
        Long payTotalPrice = cartItemList.stream()
                .mapToLong(CartItem.Response::getTotalPrice).sum();

        return new GetCartItemList(payTotalPrice, cartItemList);
    }

    //장바구니 상품 수량 증가
    @PutMapping("/{productId}/increase")
    public CartItem.Response updateCartItemAmountIncrease(@PathVariable Long productId,
                                                          @RequestParam String email) {
        return CartItem.Response.fromDto(this.cartItemService
                .updateCartItemAmountIncrease(productId, email));
    }

    //장바구니 상품 수량 감소
    @PutMapping("/{productId}/decrease")
    public CartItem.Response updateCartItemAmountDecrease(@PathVariable Long productId,
                                                          @RequestParam String email) {
        return CartItem.Response.fromDto(this.cartItemService
                .updateCartItemAmountDecrease(productId, email));
    }

    //장바구니 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeCartProduct(@PathVariable Long productId,
                                                    @RequestParam String email) {
        this.cartItemService.removeCartProduct(productId, email);

        return ResponseEntity.ok("장바구니의 상품이 삭제되었습니다.");
    }


}
