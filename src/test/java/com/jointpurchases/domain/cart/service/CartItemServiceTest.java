//package com.jointpurchases.domain.cart.service;
//
//import com.jointpurchases.domain.cart.exception.CartException;
//import com.jointpurchases.domain.cart.model.dto.CartItem;
//import com.jointpurchases.domain.cart.model.dto.CartItemDto;
//import com.jointpurchases.domain.cart.model.entity.CartEntity;
//import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
//import com.jointpurchases.domain.cart.model.entity.MemberEntity;
//import com.jointpurchases.domain.cart.repository.CartItemRepository;
//import com.jointpurchases.domain.cart.repository.CartRepository;
//import com.jointpurchases.domain.cart.repository.MemberRepository;
//import com.jointpurchases.domain.product.model.entity.Product;
//import com.jointpurchases.domain.product.model.entity.User;
//import com.jointpurchases.domain.product.repository.ProductRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static com.jointpurchases.global.exception.ErrorCode.ALREADY_EXISTS_PRODUCT_IN_CART;
//import static com.jointpurchases.global.exception.ErrorCode.NOT_EXISTS_PRODUCT_IN_CART;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class CartItemServiceTest {
//    @Mock
//    private CartRepository cartRepository;
//
//    @Mock
//    private CartItemRepository cartItemRepository;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private CartItemService cartItemService;
//
//    @Test
//    @DisplayName("장바구니에 상품 담기 성공")
//    void addProductToCart() {
//        //given
//        User member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        Product product = Product.builder()
//                .productName("김")
//                .price(1000)
//                .description("맛있는 김입니다")
//                .user(member)
//                .amount(10L)
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(5000)
//                .build();
//
//        CartItemEntity cartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(5L)
//                .productTotalPrice(5000L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(productRepository.findById(13L))
//                .willReturn(Optional.of(product));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.ofNullable(cart));
//        given(cartItemRepository.save(argThat(carted ->
//                carted.getCartEntity().equals(cart) &&
//                        carted.getProduct().equals(product) &&
//                        carted.getAmount().equals(5L) &&
//                        carted.getProductTotalPrice().equals(5000L))))
//                .willReturn(cartItem);
//
//        //when
//        CartItemDto cartItemDto = cartItemService.addProductToCart(13L, 5L, "dbdbdb@naver.com");
//
//        //then
//        assertEquals("김", cartItemDto.getProductName());
//        assertEquals(5L, cartItemDto.getAmount());
//        assertEquals(5L, cartItemDto.getCartId());
//        assertEquals(5000L, cartItemDto.getProductTotalPrice());
//
//    }
//
//    @Test
//    @DisplayName("등록하려는 상품이 장바구니에 이미 있음")
//    void addProductToCart_dupleProduct() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        Product product = Product.builder()
//                .productId(13L)
//                .productName("김")
//                .price(1000L)
//                .explanation("맛있는 김입니다")
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(13000L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(productRepository.findById(13L))
//                .willReturn(Optional.of(product));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.ofNullable(cart));
//        given(cartItemRepository.existsByCartEntityAndProductEntity(cart, product))
//                .willReturn(true); // 이미 장바구니에 있는 상품
//
//        //when
//        CartException exception = assertThrows(CartException.class, () ->
//                cartItemService.addProductToCart(13L, 10L, "dbdbdb@naver.com"));
//
//        //then
//        assertEquals(ALREADY_EXISTS_PRODUCT_IN_CART, exception.getErrorCode());
//    }
//
//    @Test
//    @DisplayName("장바구니 상품 조회")
//    void getCartList() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        Product product = Product.builder()
//                .productId(13L)
//                .productName("김")
//                .price(1000L)
//                .explanation("맛있는 김입니다")
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(5000L)
//                .build();
//
//        CartItemEntity cartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(5L)
//                .productTotalPrice(product.getPrice() * 5)
//                .build();
//
//        List<CartItemEntity> cartItemEntityList = Collections.singletonList(cartItem);
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.ofNullable(cart));
//        given(cartItemRepository.findAllByCartEntity(cart))
//                .willReturn(cartItemEntityList);
//
//        //when
//        List<CartItem.Response> cartItemList = cartItemService.getCartItemList("dbdbdb@naver.com");
//
//        //then
//        assertEquals(5000L, cartItemList.get(0).getTotalPrice());
//        assertEquals(5L, cartItemList.get(0).getAmount());
//        assertEquals("김", cartItemList.get(0).getProductName());
//    }
//
//    @Test
//    @DisplayName("장바구나 상품 조회 실패(장바구니에 담은 상품이 없음)")
//    void getCartListFail() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(13000L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByMemberEntity(member)).
//                willReturn(Optional.ofNullable(cart));
//        //빈 리스트 반환
//        given(cartItemRepository.findAllByCartEntity(cart))
//                .willReturn(new ArrayList<>());
//
//        //when
//        CartException exception =
//                assertThrows(CartException.class,
//                        () -> cartItemService.getCartItemList("dbdbdb@naver.com"));
//
//        //then
//        assertEquals(NOT_EXISTS_PRODUCT_IN_CART, exception.getErrorCode());
//    }
//
//    @Test
//    @DisplayName("장바구니 상품 수량 증가")
//    void updateCartItemAmountIncrease() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        Product product = Product.builder()
//                .productId(13L)
//                .productName("김")
//                .price(1000L)
//                .explanation("맛있는 김입니다")
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(5000L)
//                .build();
//
//        CartItemEntity cartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(5L)
//                .productTotalPrice(5000L)
//                .build();
//
//        CartItemEntity increaseCartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(6L)
//                .productTotalPrice(6000L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.of(cart));
//        given(productRepository.findByProductId(product.getProductId()))
//                .willReturn(Optional.of(product));
//        given(cartItemRepository.findByCartEntityAndProductEntity(cart, product))
//                .willReturn(Optional.ofNullable(cartItem));
//
//        given(cartItemRepository.save(argThat(carted ->
//                carted.getProduct().equals(product) &&
//                        carted.getCartEntity().equals(cart) &&
//                        carted.getAmount().equals(6L) &&
//                        carted.getProductTotalPrice().equals(6000L))))
//                .willReturn(increaseCartItem);
//
//        //when
//        CartItemDto cartDto =
//                cartItemService.updateCartItemAmountIncrease(13L, "dbdbdb@naver.com");
//
//        //then
//        assertEquals(6000L, cartDto.getProductTotalPrice());
//        assertEquals(6L, cartDto.getAmount());
//    }
//
//    @Test
//    @DisplayName("장바구니 상품 수량 감소")
//    void updateCartItemAmountDecrease() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        Product product = Product.builder()
//                .productId(13L)
//                .productName("김")
//                .price(1000L)
//                .explanation("맛있는 김입니다")
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(5000L)
//                .build();
//
//        CartItemEntity cartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(5L)
//                .productTotalPrice(product.getPrice() * 5)
//                .build();
//
//        CartItemEntity increaseCartItem = CartItemEntity.builder()
//                .cartItemId(3L)
//                .cartEntity(cart)
//                .product(product)
//                .amount(4L)
//                .productTotalPrice(product.getPrice() * 4)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.of(cart));
//        given(productRepository.findByProductId(product.getProductId()))
//                .willReturn(Optional.of(product));
//        given(cartItemRepository.findByCartEntityAndProductEntity(cart, product))
//                .willReturn(Optional.ofNullable(cartItem));
//
//        given(cartItemRepository.save(argThat(carted ->
//                carted.getProduct().equals(product) &&
//                        carted.getCartEntity().equals(cart) &&
//                        carted.getAmount().equals(4L) &&
//                        carted.getProductTotalPrice().equals(4000L))))
//                .willReturn(increaseCartItem);
//
//        //when
//        CartItemDto cartDto =
//                cartItemService.updateCartItemAmountDecrease(13L, "dbdbdb@naver.com");
//
//        //then
//        assertEquals(4000L, cartDto.getProductTotalPrice());
//        assertEquals(4L, cartDto.getAmount());
//    }
//
//    @Test
//    @DisplayName("장바구니 상품 삭제")
//    void removeCartItemProduct() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(13000L)
//                .build();
//
//        Product product = Product.builder()
//                .productId(13L)
//                .productName("김")
//                .price(1000L)
//                .explanation("맛있는 김입니다")
//                .memberEntity(member)
//                .amount(10L)
//                .build();
//
//        CartItemEntity cartItem = CartItemEntity.builder()
//                .cartEntity(cart)
//                .product(product)
//                .amount(5L)
//                .build();
//
//        given(memberRepository.findByEmail("dbdbdb@naver.com"))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByMemberEntity(member))
//                .willReturn(Optional.of(cart));
//        given(productRepository.findByProductId(product.getProductId()))
//                .willReturn(Optional.of(product));
//        given(cartItemRepository.findByCartEntityAndProductEntity(cart, product))
//                .willReturn(Optional.of(cartItem));
//
//        //when
//        cartItemService.removeCartProduct(13L, "dbdbdb@naver.com");
//    }
//}