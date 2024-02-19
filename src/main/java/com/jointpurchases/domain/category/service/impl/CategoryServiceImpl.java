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

import static com.jointpurchases.global.exception.ErrorCode.DUPLICATE_CATEGORY;
import static com.jointpurchases.global.exception.ErrorCode.NOT_FOUND_CATEGORY;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponseDto> getCategoryList() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseDto::of)
                .toList();
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        categoryNameDuplicateCheck(requestDto.categoryName());
        Category category = categoryRepository
                .save(new Category(requestDto.categoryName()));

        return CategoryResponseDto.of(category);
    }

    @Transactional
    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category category = findCategoryOrElseThrow(id);
        category.update(requestDto);

        return CategoryResponseDto.of(category);
    }

    @Override
    public Long deleteCategory(Long id) {
        Category category = findCategoryOrElseThrow(id);
        categoryRepository.delete(category);

        return category.getId();
    }
    private Category findCategoryOrElseThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryException(NOT_FOUND_CATEGORY));
    }

    private void categoryNameDuplicateCheck(String categoryName) {
        categoryRepository.findByCategoryName(categoryName)
                .ifPresent(category -> {
                    throw new CategoryException(DUPLICATE_CATEGORY);
                });
    }


}