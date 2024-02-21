package com.jointpurchases.domain.product.controller;

import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.entity.User;
import com.jointpurchases.domain.product.service.ProductService;
import com.jointpurchases.global.common.ServiceResult;
import com.jointpurchases.global.tool.LoginUser;
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
            @LoginUser final User user)
    {
        return ResponseEntity.ok()
                .body(ServiceResult.success("create success!",
                        productService.createProduct(requestDto, user, files)));
    }

    @PostMapping("/product/{productId}/like")
    public ResponseEntity<?> likeProduct(
            @PathVariable final Long productId,
            @LoginUser final User user)
    {
        return ResponseEntity.ok()
                .body(productService.likeProduct(productId, user.getId()));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable final Long id,
            @RequestPart(value = "product") @Valid ProductRequestDto requestDto,
            @RequestPart(value = "image") List<MultipartFile> files,
            @LoginUser final User user)
    {
        return ResponseEntity.ok()
                .body(ServiceResult.success("update success!",
                        productService.updateProduct(id, requestDto,user, files)));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable final Long id,
            @LoginUser final User user)
    {
        return ResponseEntity.ok()
                .body(ServiceResult.success("delete success!",
                        productService.deleteProduct(id, user)));
    }
}