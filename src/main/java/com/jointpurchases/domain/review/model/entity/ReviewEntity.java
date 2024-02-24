package com.jointpurchases.domain.review.model.entity;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEW")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
  
    private String title;

    @Column(length = 1000)
    private String contents;

    private int rating;

    private LocalDateTime registerDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public ReviewEntity(int id, String title, String contents, int rating, LocalDateTime registerDate, LocalDateTime modifiedDate, Product product, User user){
        this.title = title;
        this.contents = contents;
        this.rating = rating;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
        this.product = product;
        this.user = user;
    }

    /*
    리뷰 수정을 위한 메서드
     */
    public void updateReview(String title, String contents, int rating, LocalDateTime modifiedDate){
        this.title = title;
        this.contents = contents;
        this.rating = rating;
        this.modifiedDate = modifiedDate;
    }
}