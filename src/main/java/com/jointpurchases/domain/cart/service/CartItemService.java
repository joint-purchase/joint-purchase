package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.exception.CartException;
import com.jointpurchases.domain.cart.model.dto.CartItem;
import com.jointpurchases.domain.cart.model.dto.CartItemDto;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.repository.CartItemRepository;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jointpurchases.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    //상품 담기
    @Transactional
    public CartItemDto addProductToCart(Long productId, Integer amount, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        Product product = getProductEntity(productId);

        CartEntity cartEntity = getCartEntity(memberEntity);

        if (this.cartItemRepository.existsByCartEntityAndProduct(cartEntity, product)) {
            throw new CartException(ALREADY_EXISTS_PRODUCT_IN_CART);
        }

        return CartItemDto.fromEntity(this.cartItemRepository.save(CartItemEntity.builder()
                .cartEntity(cartEntity)
                .product(product)
                .amount(amount)
                .productTotalPrice(product.getPrice() * amount)
                .build()));
    }

    //장바구니에 담긴 상품 조회
    public List<CartItem.Response> getCartItemList(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        if (cartItemEntityList.isEmpty()) {
            throw new CartException(NOT_EXISTS_PRODUCT_IN_CART);
        }

        return cartItemEntityList.stream().map(e -> new CartItem.Response(e.getProduct().getProductName(),
                e.getAmount(), e.getProductTotalPrice())).collect(Collectors.toList());
    }

    //장바구니 상품 수량 증가
    @Transactional
    public CartItemDto updateCartItemAmountIncrease(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        Product product = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProduct(
                                cartEntity, product)
                        .orElseThrow(() -> new CartException(NOT_EXISTS_PRODUCT_IN_CART));

        if (Objects.equals(cartItemEntity.getAmount(), product.getStockQuantity())) {
            throw new CartException(DO_NOT_CHANGE_THE_QUANTITY_EXCEEDING_THE_STOCK);
        }

        cartItemEntity.increaseAmount();
        return CartItemDto.fromEntity(this.cartItemRepository.save(cartItemEntity));
    }

    //장바구니 상품 수량 감소
    @Transactional
    public CartItemDto updateCartItemAmountDecrease(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        Product product = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProduct(
                                cartEntity, product)
                        .orElseThrow(() -> new CartException(NOT_EXISTS_PRODUCT_IN_CART));

        if (cartItemEntity.getAmount() < 1) {
            throw new CartException(PRODUCT_CANNOT_BE_LESS_THAN_ONE);
        }
        cartItemEntity.decreaseAmount();

        return CartItemDto.fromEntity(this.cartItemRepository.save(cartItemEntity));
    }

    //장바구니 상품 삭제
    @Transactional
    public void removeCartProduct(Long productId, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        Product product = getProductEntity(productId);

        CartItemEntity cartItemEntity =
                this.cartItemRepository.findByCartEntityAndProduct(
                                cartEntity, product)
                        .orElseThrow(() -> new CartException(NOT_EXISTS_PRODUCT_IN_CART));

        this.cartItemRepository.delete(cartItemEntity);
    }

    private CartEntity getCartEntity(MemberEntity memberEntity) {
        return cartRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new CartException(NOT_EXISTS_USER_CART));
    }

    private MemberEntity getMemberEntity(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new CartException(NOT_EXISTS_USERID));
    }

    private Product getProductEntity(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new CartException(NOT_EXISTS_PRODUCT));
    }
}
