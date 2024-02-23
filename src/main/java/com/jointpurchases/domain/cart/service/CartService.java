package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.exception.CartException;
import com.jointpurchases.domain.cart.model.dto.CreateCart;
import com.jointpurchases.domain.cart.model.dto.DeleteCart;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.jointpurchases.global.exception.ErrorCode.*;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    //장바구니 생성
    public CreateCart.Response createCart(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        if (existsCart(memberEntity)) {
            throw new CartException(ALREADY_EXISTS_USER_CART);
        }

        return CreateCart.Response.fromEntity(this.cartRepository.save(CartEntity.builder()
                .memberEntity(memberEntity)
                .totalPrice(0)
                .build()));
    }

    //장바구니 삭제
    public DeleteCart.Response deleteCart(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = this.cartRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new CartException(NOT_EXISTS_USER_CART));

        this.cartRepository.deleteByMemberEntity(memberEntity);

        return DeleteCart.Response.fromEntity(cartEntity);
    }

    private MemberEntity getMemberEntity(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new CartException(NOT_EXISTS_USERID));
    }

    private boolean existsCart(MemberEntity memberEntity) {
        return cartRepository.existsByMemberEntity(memberEntity);
    }

}
