package com.jointpurchases.domain.point.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PointHistory {
    private Long pointId;
    private Long changedPoint;
    private Long currentPoint;
    private String eventType;
    private LocalDateTime createdDate;
}
