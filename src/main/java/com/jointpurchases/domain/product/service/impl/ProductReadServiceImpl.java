package com.jointpurchases.domain.product.service.impl;

import com.jointpurchases.domain.product.exception.ProductException;
import com.jointpurchases.domain.product.model.dto.response.ProductListResponseDto;
import com.jointpurchases.domain.product.model.dto.response.ProductResponseDto;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.domain.product.service.ProductReadService;
import com.jointpurchases.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jointpurchases.global.exception.ErrorCode.PRODUCT_NOT_FOUND;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductRepository productRepository;
    private final RedisUtil redisUtil;


    @Override
    public Page<ProductListResponseDto> getAllProduct(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllProduct(pageable);
        return productPage.map(ProductListResponseDto::of);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findProductWithProductId(id)
                .orElseThrow(()-> new ProductException(PRODUCT_NOT_FOUND));
        return ProductResponseDto.of(product, redisUtil.getLikeCount(id));
    }
}