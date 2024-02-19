package com.jointpurchases.domain.category.service;

import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.model.dto.CategoryResponseDto;
import com.jointpurchases.domain.category.model.entity.Category;
import com.jointpurchases.domain.category.repository.CategoryRepository;
import com.jointpurchases.domain.category.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    @DisplayName("카테고리 생성")
    public void createCategory() {
        // given
        String existName = "옷";
        String categoryName = "인기매물";
        CategoryRequestDto requestDto = new CategoryRequestDto(categoryName);
        Category savedCategory = new Category(categoryName);

        given(categoryRepository.save(any(Category.class)))
                .willReturn(savedCategory);

        // when
        CategoryResponseDto resultDto = categoryService.createCategory(requestDto);

        // then
        assertThat(resultDto.categoryName()).isNotEqualTo(existName);
        assertThat(resultDto.categoryName()).isNotEmpty();
        assertThat(resultDto.categoryName()).isNotNull();
        assertThat(resultDto.categoryName()).isEqualTo(categoryName);
    }

    @Test
    @DisplayName("카테고리 전체 조회")
    public void getCategoryList() {
        // given
        Category category1 = new Category("인기매물");
        Category category2 = new Category("옷");

        given(categoryRepository.findAll())
                .willReturn(Arrays.asList(category1, category2));

        // when
        List<CategoryResponseDto> result = categoryService.getCategoryList();

        // then
        assertThat(result.get(0).categoryName()).isEqualTo("인기매물");
        assertThat(result.get(1).categoryName()).isEqualTo("옷");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("카테고리 업데이트")
    public void updateCategory_UpdatesCategory() {
        // given
        Long categoryId = 1L;
        String updatedName = "인기매물";
        Category existingCategory = new Category("옷");

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(existingCategory));

        // when
        CategoryResponseDto result = categoryService.updateCategory(categoryId,
                new CategoryRequestDto(updatedName));

        // then
        assertThat(result.categoryName()).isNotEqualTo(existingCategory);
        assertThat(result.categoryName()).isNotEmpty();
        assertThat(result.categoryName()).isNotNull();
        assertThat(result.categoryName()).isEqualTo(updatedName);
    }


    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategory_DeletesCategory() {
        // given
        Long categoryId = 122L;

        Category existingCategory = new Category("인기매물");
        ReflectionTestUtils.setField(existingCategory, "id", categoryId);

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(existingCategory));

        // when
        Long resultId = categoryService.deleteCategory(categoryId);

        // then
        assertThat(categoryId).isEqualTo(resultId);
        verify(categoryRepository).delete(existingCategory);
    }


}