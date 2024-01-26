package com.jointpurchases.domain.point.controller;

import com.jointpurchases.domain.point.model.Dto.BuyPoint;
import com.jointpurchases.domain.point.model.Dto.PointHistoryResponse;
import com.jointpurchases.domain.point.model.Dto.SearchPoint;
import com.jointpurchases.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    //현금으로 포인트 구매
    @PostMapping("/point")
    public BuyPoint.Response buyPoint(@RequestBody BuyPoint.Request request) {
        return BuyPoint.Response.fromPointDto(
                this.pointService.buyPoint(request.getEmail(), request.getMoney())
        );
    }

    //현재 포인트 조회
    @GetMapping("/point")
    public SearchPoint searchPoint(@RequestParam String email) {
        return this.pointService.searchPoint(email);
    }

    //포인트 사용 내역 조회
    //기간을 입력하여 조회
    @GetMapping("/point/history")
    public PointHistoryResponse searchPointHistory(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {

        return null;
    }
}
