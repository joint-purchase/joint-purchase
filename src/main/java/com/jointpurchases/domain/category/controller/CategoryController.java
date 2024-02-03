package com.jointpurchases.domain.category.controller;

import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getCategoryList(){
        return ResponseEntity.ok()
                .body(categoryService.getCategoryList());
    }

    @PostMapping("/category")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createCategory(
            @RequestBody CategoryRequestDto requestDto)
    {
        return ResponseEntity.ok()
                .body(categoryService.createCategory(requestDto));
    }
}