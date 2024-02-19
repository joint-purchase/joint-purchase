package com.jointpurchases.domain.cart.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.Name;
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

    @Column(name = "stockQuantity")
    private Long amount;

    @Column(name = "description")
    private String explanation;

    @Column(name = "likeCount")
    private Long likes;

    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    public void decreaseStock(Long amount) {
        if (this.amount > amount) {
            this.amount -= amount;
        } else {
            throw new RuntimeException("재고가 부족합니다.");
        }
    }

    public void increaseStock(Long amount) {
        this.amount += amount;
    }

}
