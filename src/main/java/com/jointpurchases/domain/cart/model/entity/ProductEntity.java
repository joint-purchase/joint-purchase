package com.jointpurchases.domain.cart.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    private String productName;

    private Long price;
    private Long amount;

    private String explanation;

    private Long likes;

    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

}
