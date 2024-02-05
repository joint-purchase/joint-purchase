package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REVIEW_ID")
    private ReviewEntity review;
}
