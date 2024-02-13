package com.jointpurchases.domain.product.model.dto.response;

public record ProductLikeResponseDto(
        Long id,
        Integer likeCount
) {
}