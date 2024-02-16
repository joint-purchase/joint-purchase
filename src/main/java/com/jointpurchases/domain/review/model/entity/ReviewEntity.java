package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEW")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(length = 1000, nullable = false)
    private String contents;

    private int rating;

    private LocalDateTime registerDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity product;

    @Builder
    public ReviewEntity(String title, String contents, int rating, LocalDateTime registerDate, LocalDateTime modifiedDate, ProductEntity product){
        this.title = title;
        this.contents = contents;
        this.rating = rating;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
        this.product = product;
    }
/*
리뷰 수정을 위한 Setter구현
 */
    public void setTitle(String title){
        this.title = title;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setModifiedDate(LocalDateTime modifiedDate){
        this.modifiedDate = modifiedDate;
    }
}
