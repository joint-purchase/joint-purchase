package com.jointpurchases.domain.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jointpurchases.domain.auth.security.jwt.JwtAuthenticationFilter;
import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.model.dto.CategoryResponseDto;
import com.jointpurchases.domain.category.service.impl.CategoryServiceImpl;
import com.jointpurchases.global.common.ServiceResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@WebMvcTest(value = CategoryController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @MockBean
    CategoryServiceImpl categoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getCategoryList() throws Exception {
        // given
        List<CategoryResponseDto> categoryList = new ArrayList<>();
        categoryList.add(CategoryResponseDto.builder()
                .id(1L)
                .categoryName("인기매물")
                .createAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build());
        categoryList.add(CategoryResponseDto.builder()
                .id(2L)
                .categoryName("옷")
                .createAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build());

        given(categoryService.getCategoryList()).willReturn(categoryList);

        // when & then
        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("인기매물"))
                .andExpect(jsonPath("$[1].categoryName").value("옷"));
    }

    @Test
    public void createCategory_CreatesCategoryAndReturnsIt() throws Exception {
        // given
        CategoryRequestDto requestDto = new CategoryRequestDto("인기매물");
        CategoryResponseDto responseDto = CategoryResponseDto.builder()
                .id(1L)
                .categoryName("인기매물")
                .createAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(categoryService.createCategory(any(CategoryRequestDto.class)))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("인기매물"));
    }

    @Test
    public void updateCategory() throws Exception {
        // given
        Long categoryId = 1L;
        CategoryRequestDto requestDto = new CategoryRequestDto("옷");
        CategoryResponseDto responseDto = CategoryResponseDto.builder()
                .id(1L)
                .categoryName(requestDto.categoryName())
                .createAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(categoryService.updateCategory(eq(categoryId), any(CategoryRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value(responseDto.categoryName()));
    }

    @Test
    public void deleteCategory_DeletesCategoryAndReturnsSuccessMessage() throws Exception {
        // given
        Long categoryId = 1L;
        ServiceResult successResult = ServiceResult.success("delete success!", categoryId);

        given(categoryService.deleteCategory(categoryId)).willReturn(categoryId);

        // when & then
        mockMvc.perform(delete("/api/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("delete success!"));
    }
}