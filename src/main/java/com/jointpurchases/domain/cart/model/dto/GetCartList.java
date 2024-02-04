package com.jointpurchases.domain.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCartList {
    private Long payTotalPrice;
    private List<Cart.Response> cartList;
}
