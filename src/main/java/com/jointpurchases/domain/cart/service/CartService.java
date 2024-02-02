package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.model.dto.CartDto;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.member.model.entity.MemberEntity;
import com.jointpurchases.domain.member.repository.MemberRepository;
import com.jointpurchases.domain.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //장바구니에 상품 담기
    @Transactional
    public CartDto addProductToCart(Long productId, Long amount, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        ProductEntity productEntity = getProductEntity(productId);

        if(this.cartRepository.existsByProductId(productEntity.getProductId())){
            throw new RuntimeException("이미 장바구니에 담긴 상품 입니다.");
        }

        return CartDto.fromEntity(
                this.cartRepository.save(CartEntity.builder()
                        .amount(amount)
                        .productEntity(productEntity)
                        .memberEntity(memberEntity)
                        .totalPrice(productEntity.getPrice() * amount)
                        .build()));
    }

    private MemberEntity getMemberEntity(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다"));
    }

    private ProductEntity getProductEntity(Long productId) {
        return this.productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품 입니다"));
    }
}
