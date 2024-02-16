package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEWIMAGE")
public class ReviewImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String filename;
    private String filepath;
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private ReviewEntity review;

    @Builder
    public ReviewImageEntity(String filename, String filepath, LocalDateTime uploadDate, ReviewEntity review){
        this.filename = filename;
        this.filepath = filepath;
        this.uploadDate = uploadDate;
        this.review = review;
    }
}
