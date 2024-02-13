package com.jointpurchases.domain.product.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductRequestDto(

        @NotBlank(message = "상품명을 입력해주세요.")
        String productName,

        @NotBlank(message = "카테고리를 입력해주세요")
        String category,

        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 1000, message = "입력 제한을 넘었습니다.")
        String description,

        @NotNull(message = "가격을 입력해주세요.")
        @Min(value = 0 ,message = "음수는 입력할수 없습니다.")
        Integer price,

        @NotNull(message = "수량을 입력해주세요.")
        @Min(value = 0, message = "음수는 입력할수 없습니다.")
        Integer stockQuantity,

        List<MultipartFile> images
) {
}