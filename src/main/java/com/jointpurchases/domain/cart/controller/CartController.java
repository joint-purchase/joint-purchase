package com.jointpurchases.domain.cart.controller;

import com.jointpurchases.domain.cart.model.dto.CartItem;
import com.jointpurchases.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;











}
