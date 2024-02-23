package com.jointpurchases.domain.product.elasticsearch.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProductSortOption {

    LOWEST_PRICE("min", Sort.by(Sort.Direction.ASC, "price")),
    HIGHEST_PRICE("max", Sort.by(Sort.Direction.DESC, "price")),
    CREATED_AT("createDate", Sort.by(Sort.Direction.DESC, "createdAt"));

    private final String sortKey;
    private final Sort SortOption;

    public static ProductSortOption from(String sort) {
        return Arrays.stream(ProductSortOption.values())
                .filter(option -> option.sortKey.equalsIgnoreCase(sort))
                .findFirst()
                .orElse(CREATED_AT); // 기본값
    }
}