package com.jointpurchases.domain.product.controller;

import com.jointpurchases.domain.auth.sercurity.UserDetailsImpl;
import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.service.ProductService;
import com.jointpurchases.global.common.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal final UserDetailsImpl userDetails)
    {
        productService.createProduct(requestDto, userDetails.getUser(), files);
        return ResponseEntity.ok()
                .body(ServiceResult.success("create success!"));
    }

}