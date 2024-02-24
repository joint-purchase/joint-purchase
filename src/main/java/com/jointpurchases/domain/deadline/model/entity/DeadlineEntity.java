package com.jointpurchases.domain.deadline.model.entity;

import com.jointpurchases.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "DEADLINE")
public class DeadlineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int maximumPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public DeadlineEntity(int maximumPeople, LocalDateTime startDate, LocalDateTime endDate, String status, Product product){
        this.maximumPeople = maximumPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.product = product;
    }

    public void updateDeadline(LocalDateTime endDate, int maximumPeople){
        this.endDate = endDate;
        this.maximumPeople = maximumPeople;
    }
}
