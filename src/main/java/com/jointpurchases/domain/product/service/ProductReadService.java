package com.jointpurchases.domain.product.service;

import com.jointpurchases.domain.product.model.dto.response.ProductListResponseDto;
import com.jointpurchases.domain.product.model.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReadService {

    Page<ProductListResponseDto> getAllProduct(Pageable pageable);

    ProductResponseDto getProduct(Long id);
}