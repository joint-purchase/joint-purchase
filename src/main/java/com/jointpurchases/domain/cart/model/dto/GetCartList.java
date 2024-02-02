package com.jointpurchases.domain.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCartList {
    private Long payTotalPrice;
    private List<Cart.Response> cartList;
}
