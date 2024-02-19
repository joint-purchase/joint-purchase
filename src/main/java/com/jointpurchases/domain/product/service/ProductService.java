package com.jointpurchases.domain.product.service;

import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.dto.response.ProductLikeResponseDto;
import com.jointpurchases.domain.product.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Long createProduct(ProductRequestDto requestDto, User user, List<MultipartFile> files);

    ProductLikeResponseDto likeProduct(Long productId, Long userId);

    Long updateProduct(Long id, ProductRequestDto requestDto, User user ,List<MultipartFile> files);

    Long deleteProduct(Long id, User user);

}