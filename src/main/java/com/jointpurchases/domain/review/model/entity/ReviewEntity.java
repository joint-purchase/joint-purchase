package com.jointpurchases.domain.review.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "REVIEW")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String contents;
    private int rating;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    @Builder
    public ReviewEntity(String title, String contents, int rating, LocalDateTime registerDate, LocalDateTime modifiedDate){
        this.title = title;
        this.contents = contents;
        this.rating = rating;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
