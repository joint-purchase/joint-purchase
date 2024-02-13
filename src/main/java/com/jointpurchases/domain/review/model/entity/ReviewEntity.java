package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEW")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(length = 1000, nullable = false)
    private String contents;

    private int rating;

    private LocalDateTime registerDate;

    private LocalDateTime modifiedDate;
/*
Setter 구현
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

    public void setRegisterDate(LocalDateTime registerDate){
        this.registerDate = registerDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate){
        this.modifiedDate = modifiedDate;
    }


    @Builder
    public ReviewEntity(String title, String contents, int rating, LocalDateTime registerDate, LocalDateTime modifiedDate){
        this.title = title;
        this.contents = contents;
        this.rating = rating;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }

}
