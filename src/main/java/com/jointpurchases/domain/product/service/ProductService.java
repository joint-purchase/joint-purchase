package com.jointpurchases.domain.product.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.dto.response.ProductLikeResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequestDto requestDto, User user, List<MultipartFile> files);

    void updateProduct(Long id, ProductRequestDto requestDto, User user ,List<MultipartFile> files);
}