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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

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
                .price(1000L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(5L)
                .memberEntity(member)
                .totalPrice(13000L)
                .build();

        CartItemEntity cartItem = CartItemEntity.builder()
                .cartItemId(3L)
                .cartEntity(cart)
                .productEntity(product)
                .amount(5L)
                .productTotalPrice(product.getPrice() * 5)
                .build();

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));
        given(productRepository.findByProductId(13L))
                .willReturn(Optional.of(product));
        given(cartRepository.findByMemberEntity(member))
                .willReturn(Optional.ofNullable(cart));
        given(cartItemRepository.save(argThat(carted ->
                carted.getCartEntity().equals(cart) &&
                        carted.getProductEntity().equals(product) &&
                        carted.getAmount().equals(5L) &&
                        carted.getProductTotalPrice().equals(5000L))))
                .willReturn(cartItem);

        //when
        CartItemDto cartItemDto = cartItemService.addProductToCart(13L, 10L, "dbdbdb@naver.com");

        //then
        assertEquals("김", cartItemDto.getProductName());
        assertEquals(5L, cartItemDto.getAmount());
        assertEquals(5L, cartItemDto.getCartId());
        assertEquals(5000L, cartItemDto.getProductTotalPrice());

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
                .price(1000L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(5L)
                .memberEntity(member)
                .totalPrice(13000L)
                .build();

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));
        given(productRepository.findByProductId(13L))
                .willReturn(Optional.of(product));
        given(cartRepository.findByMemberEntity(member))
                .willReturn(Optional.ofNullable(cart));
        given(cartItemRepository.existsByCartEntityAndProductEntity(cart, product))
                .willReturn(true); // 이미 장바구니에 있는 상품

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                cartItemService.addProductToCart(13L, 10L, "dbdbdb@naver.com"));

        //then
        assertEquals("이미 장바구니에 담긴 상품 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("장바구니 상품 조회")
    void getCartList() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        ProductEntity product = ProductEntity.builder()
                .productId(13L)
                .productName("김")
                .price(1000L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(5L)
                .memberEntity(member)
                .totalPrice(5000L)
                .build();

        CartItemEntity cartItem = CartItemEntity.builder()
                .cartItemId(3L)
                .cartEntity(cart)
                .productEntity(product)
                .amount(5L)
                .productTotalPrice(product.getPrice() * 5)
                .build();

        List<CartItemEntity> cartItemEntityList = Collections.singletonList(cartItem);

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));
        given(cartRepository.findByMemberEntity(member))
                .willReturn(Optional.ofNullable(cart));
        given(cartItemRepository.findAllByCartEntity(cart))
                .willReturn(cartItemEntityList);

        //when
        List<CartItem.Response> cartItemList = cartItemService.getCartItemList("dbdbdb@naver.com");

        //then
        assertEquals(5000L, cartItemList.get(0).getTotalPrice());
        assertEquals(5L, cartItemList.get(0).getAmount());
        assertEquals("김", cartItemList.get(0).getProductName());
    }

    @Test
    @DisplayName("장바구나 상품 조회 실패(장바구니에 담은 상품이 없음)")
    void getCartListFail() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(5L)
                .memberEntity(member)
                .totalPrice(13000L)
                .build();

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));
        given(cartRepository.findByMemberEntity(member)).
                willReturn(Optional.ofNullable(cart));
        //빈 리스트 반환
        given(cartItemRepository.findAllByCartEntity(cart))
                .willReturn(new ArrayList<>());

        //when
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> cartItemService.getCartItemList("dbdbdb@naver.com"));

        //then
        assertEquals("장바구니에 담은 상품이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("장바구니 상품 수량 증가")
    void updateCartAmountIncrease() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        ProductEntity product = ProductEntity.builder()
                .productId(13L)
                .productName("김")
                .price(1000L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(5L)
                .memberEntity(member)
                .totalPrice(13000L)
                .build();

        CartItemEntity cartItem = CartItemEntity.builder()
                .cartItemId(3L)
                .cartEntity(cart)
                .productEntity(product)
                .amount(5L)
                .productTotalPrice(product.getPrice() * 5)
                .build();

        CartEntity increaseCart = CartEntity.builder()
                .cartId(3L)
                .totalPrice(11000L)
                .productEntity(product)
                .memberEntity(member)
                .amount(11L)
                .build();

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));

        given(cartRepository.findByCartId(3L))
                .willReturn(Optional.ofNullable(cart));

        given(cartRepository.save(argThat(carted ->
                carted.getProductEntity().equals(product) &&
                        carted.getMemberEntity().equals(member) &&
                        carted.getAmount().equals(11L) &&
                        carted.getTotalPrice().equals(11000L))))
                .willReturn(increaseCart);

        //when
        CartItemDto cartDto =
                cartService.updateCartAmountIncrease(3L, "dbdbdb@naver.com");

        //then
        assertEquals(11000L, cartDto.getProductTotalPrice());
        assertEquals(11L, cartDto.getAmount());
    }

    @Test
    @DisplayName("장바구니 상품 수량 감소")
    void updateCartAmountDecrease() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        ProductEntity product = ProductEntity.builder()
                .productId(13L)
                .productName("김")
                .price(1000L)
                .explanation("맛있는 김입니다")
                .memberEntity(member)
                .amount(11L)
                .build();

        CartEntity cart = CartEntity.builder()
                .cartId(3L)
                .totalPrice(10000L)
                .productEntity(product)
                .memberEntity(member)
                .amount(10L)
                .build();

        CartEntity decreaseCart = CartEntity.builder()
                .cartId(3L)
                .totalPrice(9000L)
                .productEntity(product)
                .memberEntity(member)
                .amount(9L)
                .build();

        given(memberRepository.findByEmail("dbdbdb@naver.com"))
                .willReturn(Optional.of(member));

        given(cartRepository.findByCartId(3L))
                .willReturn(Optional.ofNullable(cart));

        given(cartRepository.save(argThat(carted ->
                carted.getProductEntity().equals(product) &&
                        carted.getMemberEntity().equals(member) &&
                        carted.getAmount().equals(9L) &&
                        carted.getTotalPrice().equals(9000L))))
                .willReturn(decreaseCart);

        //when
        CartItemDto cartDto =
                cartService.updateCartAmountDecrease(3L, "dbdbdb@naver.com");

        //then
        assertEquals(9000L, cartDto.getProductTotalPrice());
        assertEquals(9L, cartDto.getAmount());
    }

//    @Test
//    @DisplayName("장바구니 상품 삭제")
//    void removeCartProduct() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(3L)
//                .totalPrice(13000L)
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//
//        given(cartRepository.findByCartId(3L))
//                .willReturn(Optional.ofNullable(cart));
//
//        //when
//        cartService.removeCartProduct(3L, "dbdbdb@naver.com");
//    }
}