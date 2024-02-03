package com.jointpurchases.domain.category.controller;

import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.domain.category.service.CategoryService;
import com.jointpurchases.global.common.ServiceResult;
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

    @PutMapping("/category/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateCategory(
            @PathVariable final Long id,
            @RequestBody CategoryRequestDto requestDto)
    {
        return ResponseEntity.ok()
                .body(categoryService.updateCategory(id, requestDto));
    }

    @DeleteMapping("/category/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteCategory(
            @PathVariable final Long id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok()
                .body(ServiceResult.success("delete success!"));
    }
}