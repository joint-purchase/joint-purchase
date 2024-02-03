package com.jointpurchases.domain.category.service.impl;

import com.jointpurchases.domain.category.exception.CategoryException;
import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.model.dto.CategoryResponseDto;
import com.jointpurchases.domain.category.model.entity.Category;
import com.jointpurchases.domain.category.repository.CategoryRepository;
import com.jointpurchases.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jointpurchases.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        categoryNameDuplicateCheck(requestDto.categoryName());
        Category category = categoryRepository
                .save(new Category(requestDto.categoryName()));

        return CategoryResponseDto.of(category);
    }


    private void categoryNameDuplicateCheck(String categoryName) {
        categoryRepository.findByCategoryName(categoryName)
                .ifPresent(category -> {
                    throw new CategoryException(DUPLICATE_CATEGORY);
                });
    }

}