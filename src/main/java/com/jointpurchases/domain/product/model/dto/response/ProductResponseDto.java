package com.jointpurchases.domain.product.model.dto.response;

import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.model.entity.ProductImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductResponseDto(
        Long id,
        String productName,
        String userName,
        String description,
        Integer price,
        Integer stockQuantity,
        Integer likeCount,
        String categoryName,
        List<String> imageList,
        LocalDateTime createDate,
        LocalDateTime modifiedDate
) {

    public static ProductResponseDto of(Product product, int like) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .userName(product.getUser().getUsername())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .likeCount(product.getLikeCount() + like)
                .imageList(product.getProductImages()
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .toList())
                .createDate(product.getCreatedAt())
                .modifiedDate(product.getModifiedAt())
                .build();
    }
}