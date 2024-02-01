package com.jointpurchases.domain.review.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;
    private String filepath;
    private LocalDateTime uploadDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;
}
