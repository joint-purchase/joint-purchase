package com.jointpurchases.domain.category.model.dto;

import com.jointpurchases.domain.category.model.entity.Category;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryResponseDto (
        Long id,
        String categoryName,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
){

    public static CategoryResponseDto of(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createAt(category.getCreatedAt())
                .modifiedAt(category.getModifiedAt())
                .build();
    }
}