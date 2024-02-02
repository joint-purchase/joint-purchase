package com.jointpurchases.domain.category.service;

import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.model.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getCategoryList();

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

}