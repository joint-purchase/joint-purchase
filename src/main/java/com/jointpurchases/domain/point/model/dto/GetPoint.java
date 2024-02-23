package com.jointpurchases.domain.point.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class GetPoint {
    private Integer currentPoint;
}
