package com.jointpurchases.domain.product.controller;

import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.entity.User;
import com.jointpurchases.domain.product.service.ProductService;
import com.jointpurchases.global.common.ServiceResult;
import com.jointpurchases.global.tool.LoginMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/product")
    public ResponseEntity<?> createProduct(
            @RequestPart(value = "product") @Valid ProductRequestDto requestDto,
            @RequestPart(value = "image") List<MultipartFile> files,
            @LoginMember final User user)
    {
        productService.createProduct(requestDto, user, files);
        return ResponseEntity.ok()
                .body(ServiceResult.success("create success!"));
    }

    @PostMapping("/product/{productId}/like")
    public ResponseEntity<?> likeProduct(
            @PathVariable final Long productId,
            @LoginMember final User user)
    {
        return ResponseEntity.ok()
                .body(productService.likeProduct(productId, user.getId()));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable final Long id,
            @RequestPart(value = "product") @Valid ProductRequestDto requestDto,
            @RequestPart(value = "image") List<MultipartFile> files,
            @LoginMember final User user)
    {
        productService.updateProduct(id, requestDto, user, files);
        return ResponseEntity.ok()
                .body(ServiceResult.success("update success!"));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable final Long id,
            @LoginMember final User user)
    {
        productService.deleteProduct(id, user);
        return ResponseEntity.ok()
                .body(ServiceResult.success("delete success!"));
    }
}