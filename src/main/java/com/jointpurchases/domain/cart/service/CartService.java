package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.model.dto.Cart;
import com.jointpurchases.domain.cart.model.dto.CartDto;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import com.jointpurchases.domain.cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        if (this.cartRepository.existsByProductEntityProductId(productEntity.getProductId())) {
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

    //장바구니에 담긴 상품 조회
    public List<Cart.Response> getCartList(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        List<CartEntity> cartEntityList =
                this.cartRepository.findAllByMemberEntity(memberEntity);

        if (cartEntityList.isEmpty()) {
            throw new RuntimeException("장바구니에 담은 상품이 없습니다.");
        }

        return cartEntityList.stream().map(e -> new Cart.Response(e.getProductEntity().getProductName(),
                e.getAmount(), e.getTotalPrice())).collect(Collectors.toList());
    }

    //장바구니 상품 수량 증가
    @Transactional
    public CartDto updateCartAmountIncrease(Long cartId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(cartId);

        userCartNameMatch(cartEntity, memberEntity);

        cartEntity.increaseAmount();
        return CartDto.fromEntity(this.cartRepository.save(cartEntity));
    }

    //장바구니 상품 수량 감소
    @Transactional
    public CartDto updateCartAmountDecrease(Long cartId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(cartId);

        userCartNameMatch(cartEntity, memberEntity);

        cartEntity.decreaseAmount();

        if (cartEntity.getAmount() < 1) {
            throw new RuntimeException("장바구니 상품의 수량은 1보다 작을 수 없습니다");
        }
        return CartDto.fromEntity(this.cartRepository.save(cartEntity));
    }

    //장바구니 상품 삭제
    @Transactional
    public void removeCartProduct(Long cartId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(cartId);

        userCartNameMatch(cartEntity, memberEntity);

        this.cartRepository.deleteByCartId(cartId);
    }

    private static void userCartNameMatch(CartEntity cartEntity, MemberEntity memberEntity) {
        if (!Objects.equals(cartEntity.getMemberEntity().getEmail(), memberEntity.getEmail())) {
            throw new RuntimeException("장바구니 사용자가 일치하지 않습니다.");
        }
    }

    private CartEntity getCartEntity(Long cartId) {
        return this.cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("잘못된 장바구니 정보 입니다."));
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
