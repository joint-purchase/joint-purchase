package com.jointpurchases.domain.product.model.dto.response;

import com.jointpurchases.domain.product.elasticsearch.document.ProductDocument;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.model.entity.ProductImage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductListResponseDto(
        Long id,
        String categoryName,
        String productName,
        Integer price,
        String imageUrl,
        LocalDateTime createDate,
        LocalDateTime modifiedDate

) {

    public static ProductListResponseDto of(Product product) {
        return ProductListResponseDto.builder()
                .id(product.getId())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .price(product.getPrice())
                .imageUrl(product.getProductImages().stream()
                        .findFirst()
                        .map(ProductImage::getImageUrl)
                        .orElse(null))
                .createDate(product.getCreatedAt())
                .modifiedDate(product.getModifiedAt())
                .build();
    }

    public static ProductListResponseDto of(ProductDocument product) {
        return ProductListResponseDto.builder()
                .id(product.getId())
                .categoryName(product.getCategoryName())
                .productName(product.getProductName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .createDate(product.getCreatedAt())
                .modifiedDate(product.getModifiedAt())
                .build();
    }
}