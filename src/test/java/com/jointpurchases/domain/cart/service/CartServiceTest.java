//package com.jointpurchases.domain.cart.service;
//
//import com.jointpurchases.domain.cart.exception.CartException;
//import com.jointpurchases.domain.cart.model.dto.CreateCart;
//import com.jointpurchases.domain.cart.model.dto.DeleteCart;
//import com.jointpurchases.domain.cart.model.entity.CartEntity;
//import com.jointpurchases.domain.cart.repository.CartRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static com.jointpurchases.global.exception.ErrorCode.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class CartServiceTest {
//    @Mock
//    private CartRepository cartRepository;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private CartService cartService;
//
//    @Test
//    void createCartSuccess() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(0L)
//                .build();
//
//        given(memberRepository.findByEmail(member.getEmail()))
//                .willReturn(Optional.of(member));
//        given(cartRepository.save(argThat(cartEntity ->
//                cartEntity.getMemberEntity().equals(member) &&
//                        cartEntity.getTotalPrice().equals(0L))))
//                .willReturn(cart);
//
//        //when
//        CreateCart.Response cartResponse = cartService.createCart(member.getEmail());
//
//        //then
//        assertEquals(5L, cartResponse.getCartId());
//    }
//
//    @Test
//    void createCartFail() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        given(memberRepository.findByEmail(member.getEmail()))
//                .willReturn(Optional.of(member));
//        given(cartRepository.existsByUserEntity(member))
//                .willReturn(true);
//
//        //when
//        CartException exception = assertThrows(CartException.class,
//                () -> cartService.createCart(member.getEmail()));
//
//        //then
//        assertEquals(ALREADY_EXISTS_USER_CART, exception.getErrorCode());
//
//    }
//
//    @Test
//    void deleteCartSuccess() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        CartEntity cart = CartEntity.builder()
//                .cartId(5L)
//                .memberEntity(member)
//                .totalPrice(0L)
//                .build();
//
//        given(memberRepository.findByEmail(member.getEmail()))
//                .willReturn(Optional.of(member));
//        given(cartRepository.findByUserEntity(member))
//                .willReturn(Optional.ofNullable(cart));
//
//        //when
//        DeleteCart.Response cartResponse = cartService.deleteCart(member.getEmail());
//
//        //then
//        assertEquals(5L, cartResponse.getCartId());
//    }
//
//    @Test
//    void deleteCartFail() {
//        //given
//        MemberEntity member = MemberEntity.builder()
//                .id(11L)
//                .email("dbdbdb@naver.com")
//                .build();
//
//        given(memberRepository.findByEmail(member.getEmail()))
//                .willReturn(Optional.of(member));
//
//        //when
//        CartException exception = assertThrows(CartException.class,
//                () -> cartService.deleteCart(member.getEmail()));
//
//        //then
//        assertEquals(NOT_EXISTS_USER_CART, exception.getErrorCode());
//    }
//
//}