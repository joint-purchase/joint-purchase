package com.jointpurchases.domain.category.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank(message = "카테코리명을 입력해주세요.")
        String categoryName
) {

}