package com.jointpurchases.domain.point.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPoint {
    private Long currentPoint;
}
