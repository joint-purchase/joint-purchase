package com.jointpurchases.domain.cart.service;

import com.jointpurchases.domain.cart.model.dto.CartItem;
import com.jointpurchases.domain.cart.model.dto.CartItemDto;
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

}
