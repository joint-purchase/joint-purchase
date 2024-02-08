package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEWIMAGE")
public class ReviewImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;
    private String filepath;
    private LocalDateTime uploadDate;

    public void setFilename(String filename){
        this.filename = filename;
    }

    public void setFilepath(String filepath){
        this.filepath = filepath;
    }

    public void setUploadDate(LocalDateTime uploadDate){
        this.uploadDate = uploadDate;
    }


    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private ReviewEntity review;
/*
Setter 구현
 */
    public void setReview(ReviewEntity review){
        this.review = review;
    }

    @Builder
    public ReviewImageEntity(String filename, String filepath, LocalDateTime uploadDate, ReviewEntity review){
        this.filename = filename;
        this.filepath = filepath;
        this.uploadDate = uploadDate;
        this.review = review;
    }
}
