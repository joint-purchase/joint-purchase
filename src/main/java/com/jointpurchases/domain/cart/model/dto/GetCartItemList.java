package com.jointpurchases.domain.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCartItemList {
    private Long payTotalPrice;
    private List<CartItem.Response> cartList;
}
