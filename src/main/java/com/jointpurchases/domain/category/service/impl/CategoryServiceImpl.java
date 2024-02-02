package com.jointpurchases.domain.category.service.impl;

import com.jointpurchases.domain.category.exception.CategoryException;
import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.model.dto.CategoryResponseDto;
import com.jointpurchases.domain.category.model.entity.Category;
import com.jointpurchases.domain.category.repository.CategoryRepository;
import com.jointpurchases.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.jointpurchases.global.exception.ErrorCode.DUPLICATE_CATEGORY;

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

    private void categoryNameDuplicateCheck(String categoryName) {
        categoryRepository.findByCategoryName(categoryName)
                .ifPresent(category -> {
                    throw new CategoryException(DUPLICATE_CATEGORY);
                });
    }

}