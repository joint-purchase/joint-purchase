package com.jointpurchases.domain.product.model.entity;

import com.jointpurchases.domain.category.model.entity.Category;

import com.jointpurchases.domain.product.model.dto.ProductRequestDto;
import com.jointpurchases.domain.product.model.dto.request.ProductRequestDto;

import com.jointpurchases.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    @Builder
    public Product(String productName, String description, Integer price,
                   Integer stockQuantity, Integer likeCount, User user, Category category) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.likeCount = likeCount;
        this.user = user;
        this.category = category;
    }

    public void update(ProductRequestDto requestDto, List<ProductImage> productImages, Category category) {
        this.category = category != null ? category : this.category;
        this.productName = requestDto.productName() != null ? requestDto.productName() : this.productName;
        this.description = requestDto.description() != null ? requestDto.description() : this.description;
        this.stockQuantity = requestDto.stockQuantity() >= 0 ? requestDto.stockQuantity() : this.stockQuantity;
        this.price = requestDto.price() >= 0 ? requestDto.price() : this.price;
        if (!productImages.isEmpty()) {
            this.productImages.clear();
            this.productImages.addAll(productImages);
        }
    }

    public void likeCountUpdate(Integer likeCount){
        this.likeCount = likeCount;
    }
}