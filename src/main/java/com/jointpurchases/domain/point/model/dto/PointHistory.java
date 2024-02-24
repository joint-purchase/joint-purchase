package com.jointpurchases.domain.point.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointHistory {
    private Long pointId;
    private Integer changedPoint;
    private Integer currentPoint;
    private String eventType;
    private LocalDateTime createdDate;
}
