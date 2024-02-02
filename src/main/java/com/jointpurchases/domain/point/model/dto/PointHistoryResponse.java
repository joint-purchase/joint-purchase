package com.jointpurchases.domain.point.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class PointHistoryResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PointHistory> pointHistoryDtoList;
}
