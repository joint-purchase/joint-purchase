package com.jointpurchases.domain.product.service.impl;

import com.jointpurchases.domain.category.exception.CategoryException;
import com.jointpurchases.domain.category.model.entity.Category;
import com.jointpurchases.domain.category.repository.CategoryRepository;
import com.jointpurchases.domain.product.elasticsearch.document.ProductDocument;
import com.jointpurchases.domain.product.elasticsearch.repository.ProductDocumentRepository;
import com.jointpurchases.domain.product.exception.ProductException;
import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.dto.response.ProductLikeResponseDto;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.model.entity.ProductImage;
import com.jointpurchases.domain.product.model.entity.User;
import com.jointpurchases.domain.product.repository.ProductImageRepository;
import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.domain.product.service.ProductService;
import com.jointpurchases.global.util.RedisUtil;
import com.jointpurchases.global.util.S3ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.jointpurchases.global.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final S3ImageUtil s3ImageService;
    private final RedisUtil redisUtil;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductDocumentRepository productDocumentRepository;


    private static final int MAXIMUM_IMAGES = 5;    // 이미지 업로드 최대 개수


    @Transactional
    @Override
    public Long createProduct(ProductRequestDto requestDto, User user, List<MultipartFile> files) {
        Category category = findCategoryOrElseThrow(requestDto.category());
        checkImageUploadLimit(files);

        Product product = productRepository.save(Product.builder()
                        .user(user)
                        .category(category)
                        .productName(requestDto.productName())
                        .description(requestDto.description())
                        .stockQuantity(requestDto.stockQuantity())
                        .price(requestDto.price())
                        .likeCount(0)
                        .build());

        List<ProductImage> productImages = imageUpload(product, files);
        productImageRepository.saveAll(productImages);

        elasticsearchOperations.save(ProductDocument.of(product, productImages));
        return product.getId();
    }

    @Override
    public ProductLikeResponseDto likeProduct(Long productId, Long userId) {
        Product product = findProductOrElseThrow(productId);

        if (Boolean.TRUE.equals(redisUtil.isLike(userId, productId))) {
            redisUtil.unLike(userId, productId);
        } else {
            redisUtil.like(userId, productId);
        }

        return new ProductLikeResponseDto(
                productId, product.getLikeCount() + redisUtil.getLikeCount(productId));
    }

    @Transactional
    @Override
    public Long updateProduct(Long id, ProductRequestDto requestDto, User user, List<MultipartFile> files) {
        checkImageUploadLimit(files);
        Category category = findCategoryOrElseThrow(requestDto.category());
        Product product = findProductOrElseThrow(id);
        checkUserProduct(product, user);

        List<ProductImage> productImages = imageUpload(product, files);

        deleteImageS3(product.getProductImages());
        product.update(requestDto, productImages, category);

        ProductDocument productDocument = findProductDocumentOrElseThrow(product.getId());
        productDocument.update(requestDto, productImages.stream()
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(null));

        elasticsearchOperations.save(productDocument);

        return product.getId();
    }

    @Transactional
    @Override
    public Long deleteProduct(Long id, User user) {
        Product product = findProductOrElseThrow(id);
        checkUserProduct(product, user);

        deleteImageS3(product.getProductImages());
        productRepository.delete(product);

        ProductDocument productDocument = findProductDocumentOrElseThrow(product.getId());
        elasticsearchOperations.delete(productDocument);

        return product.getId();
    }


    private void deleteImageS3(List<ProductImage> images) {
        if (images.isEmpty()) return;

        try {
            images.forEach(e ->
                    s3ImageService.deleteImage(e.getImageUrl()));
        }catch (Exception e){
            log.error("delete product image failed. file={}", e.getMessage(), e);
        }
    }

    private void checkUserProduct(Product product, User user) {
        if (!Objects.equals(product.getUser().getId(), user.getId())) {
            throw new ProductException(NOT_PRODUCT_BY_USER);
        }
    }

    private ProductDocument findProductDocumentOrElseThrow(Long id){
        return productDocumentRepository.findById(id)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));
    }

    private Product findProductOrElseThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductException(PRODUCT_NOT_FOUND));
    }

    private List<ProductImage> imageUpload(Product product, List<MultipartFile> files) {
        List<String> imageNameList = s3ImageService.upload(files);
        return imageNameList
                .stream()
                .map(imageUrl -> new ProductImage(imageUrl, product))
                .toList();
    }

    private void checkImageUploadLimit(List<MultipartFile> files) {
        if (files.size() > MAXIMUM_IMAGES) {
            throw new ProductException(UPLOAD_MAXIMUM_IMAGE);
        }
    }

    private Category findCategoryOrElseThrow(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(()-> new CategoryException(NOT_FOUND_CATEGORY));
    }


}