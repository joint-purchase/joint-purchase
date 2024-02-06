package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.model.dto.CartDto;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import com.jointpurchases.domain.cart.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 상품 담기 성공")
    void addProductToCart() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        ProductEntity product = ProductEntity.builder()
                .productId(13L)
                .productName("김")
                .price(1300L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(3L)
                .totalPrice(13000L)
                .productEntity(product)
                .memberEntity(member)
                .amount(10L)
                .build();

        //when
        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));

        given(productRepository.findByProductId(13L))
                .willReturn(Optional.of(product));

        given(cartRepository.save(any()))
                .willReturn(cart);

        CartDto cartDto = cartService.addProductToCart(13L, 10L, "dbdbdb@naver.com");

        //then
        assertEquals("김", cartDto.getProductName());
        assertEquals(10L, cartDto.getAmount());
        assertEquals(3L, cartDto.getCartId());
        assertEquals(13000L, cartDto.getTotalPrice());

    }

    @Test
    @DisplayName("등록하려는 상품이 장바구니에 이미 있음")
    void addProductToCart_dupleProduct() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        ProductEntity product = ProductEntity.builder()
                .productId(13L)
                .productName("김")
                .price(1300L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        //when
        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));

        given(productRepository.findByProductId(13L))
                .willReturn(Optional.of(product));

        given(cartRepository.existsByProductEntity(product))
                .willReturn(true); // 이미 장바구니에 있는 상품

        //then
        assertThrows(RuntimeException.class, () ->
                cartService.addProductToCart(13L, 10L, "dbdbdb@naver.com"));
    }



}