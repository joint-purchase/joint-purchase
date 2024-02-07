package com.jointpurchases.domain.category.model.entity;

import com.jointpurchases.domain.category.model.dto.CategoryRequestDto;
import com.jointpurchases.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public void update(CategoryRequestDto requestDto) {
        this.categoryName = requestDto.categoryName() != null ? requestDto.categoryName() : this.getCategoryName();
    }

}