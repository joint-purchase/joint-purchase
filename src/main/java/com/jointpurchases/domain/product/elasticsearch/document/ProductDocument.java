package com.jointpurchases.domain.product.elasticsearch.document;

import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.model.entity.ProductImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "product")
@Getter
@Setting(settingPath = "/elastic-settings.json")
@Mapping(mappingPath = "/product-mappings.json")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDocument {

    @Id
    private Long id;

    private String productName;

    private Integer price;

    private String categoryName;

    private String imageUrl;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime modifiedAt;

    @Builder
    public ProductDocument(Long id, String productName, Integer price, String categoryName, String imageUrl,
                           LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void update(ProductRequestDto requestDto, String imageUrl) {
        this.productName = requestDto.productName();
        this.categoryName = requestDto.category();
        this.price = requestDto.price();
        this.imageUrl = imageUrl;
    }

    public static ProductDocument of(Product product, List<ProductImage> productImages) {
        return ProductDocument.builder()
                .id(product.getId())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .price(product.getPrice())
                .imageUrl(productImages.stream()
                        .findFirst()
                        .map(ProductImage::getImageUrl)
                        .orElse(null))
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }



}