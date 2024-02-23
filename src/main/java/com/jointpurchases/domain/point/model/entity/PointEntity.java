package com.jointpurchases.domain.point.model.entity;

import com.jointpurchases.domain.auth.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity(name = "point")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer changedPoint;
    private Integer currentPoint;

    private String eventType;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userEntity;
}
