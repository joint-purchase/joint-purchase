package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.cart.exception.CartException;
import com.jointpurchases.domain.cart.model.dto.CreateCart;
import com.jointpurchases.domain.cart.model.dto.DeleteCart;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.jointpurchases.global.exception.ErrorCode.ALREADY_EXISTS_USER_CART;
import static com.jointpurchases.global.exception.ErrorCode.NOT_EXISTS_USER_CART;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    //장바구니 생성
    public CreateCart.Response createCart(User userEntity) {
        if (existsCart(userEntity)) {
            throw new CartException(ALREADY_EXISTS_USER_CART);
        }

        return CreateCart.Response.fromEntity(this.cartRepository.save(CartEntity.builder()
                .userEntity(userEntity)
                .totalPrice(0)
                .build()));
    }

    //장바구니 삭제
    public DeleteCart.Response deleteCart(User userEntity) {
        CartEntity cartEntity = this.cartRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new CartException(NOT_EXISTS_USER_CART));

        this.cartRepository.deleteByUserEntity(userEntity);

        return DeleteCart.Response.fromEntity(cartEntity);
    }

    private boolean existsCart(User userEntity) {
        return cartRepository.existsByUserEntity(userEntity);
    }

}
