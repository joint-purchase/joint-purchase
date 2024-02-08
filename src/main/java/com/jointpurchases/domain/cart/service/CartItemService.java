package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.model.dto.CartItem;
import com.jointpurchases.domain.cart.model.dto.CartItemDto;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import com.jointpurchases.domain.cart.repository.CartItemRepository;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import com.jointpurchases.domain.cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CartItemDto addProductToCart(Long productId, Long amount, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        ProductEntity productEntity = getProductEntity(productId);

        CartEntity cartEntity = getCartEntity(memberEntity);

        if (this.cartItemRepository.existsByCartEntityAndProductEntity(cartEntity, productEntity)) {
            throw new RuntimeException("이미 장바구니에 담긴 상품 입니다.");
        }

        return CartItemDto.fromEntity(this.cartItemRepository.save(CartItemEntity.builder()
                .cartEntity(cartEntity)
                .productEntity(productEntity)
                .amount(amount)
                .productTotalPrice(productEntity.getPrice() * amount)
                .build()));
    }

    //장바구니에 담긴 상품 조회
    public List<CartItem.Response> getCartItemList(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        if (cartItemEntityList.isEmpty()) {
            throw new RuntimeException("장바구니에 담은 상품이 없습니다.");
        }

        return cartItemEntityList.stream().map(e -> new CartItem.Response(e.getProductEntity().getProductName(),
                e.getAmount(), e.getProductTotalPrice())).collect(Collectors.toList());
    }

    //장바구니 상품 수량 증가
    @Transactional
    public CartItemDto updateCartItemAmountIncrease(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        ProductEntity productEntity = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProductEntity(
                                cartEntity, productEntity)
                        .orElseThrow(() -> new RuntimeException("사용자의 장바구니에 해당 상품이 없습니다."));

        if (Objects.equals(cartItemEntity.getAmount(), productEntity.getAmount())) {
            throw new RuntimeException("상품의 남은 수량을 초과합니다.");
        }

        cartItemEntity.increaseAmount();
        return CartItemDto.fromEntity(this.cartItemRepository.save(cartItemEntity));
    }

    //장바구니 상품 수량 감소
    @Transactional
    public CartItemDto updateCartItemAmountDecrease(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        ProductEntity productEntity = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProductEntity(
                                cartEntity, productEntity)
                        .orElseThrow(() -> new RuntimeException("사용자의 장바구니에 해당 상품이 없습니다."));

        if (cartItemEntity.getAmount() < 1) {
            throw new RuntimeException("장바구니 상품의 수량은 1보다 작을 수 없습니다");
        }
        cartItemEntity.decreaseAmount();

        return CartItemDto.fromEntity(this.cartItemRepository.save(cartItemEntity));
    }

    //장바구니 상품 삭제
    @Transactional
    public void removeCartProduct(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        ProductEntity productEntity = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProductEntity(
                                cartEntity, productEntity)
                        .orElseThrow(() -> new RuntimeException("사용자의 장바구니에 해당 상품이 없습니다."));

        this.cartItemRepository.delete(cartItemEntity);
    }

    private CartEntity getCartEntity(MemberEntity memberEntity) {
        return cartRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
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
