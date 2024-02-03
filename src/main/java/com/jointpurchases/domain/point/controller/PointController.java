package com.jointpurchases.domain.point.controller;

import com.jointpurchases.domain.point.model.dto.BuyPoint;
import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.model.dto.PointHistoryResponse;
import com.jointpurchases.domain.point.model.dto.RefundPoint;
import com.jointpurchases.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController("/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    //현금으로 포인트 구매
    @PostMapping
    public BuyPoint.Response buyPoint(@RequestBody BuyPoint.Request request) {
        return BuyPoint.Response.fromPointDto(
                this.pointService.buyPoint(request.getEmail(), request.getMoney())
        );
    }

    //현재 포인트 조회
    @GetMapping
    public GetPoint getCurrentPoint(@RequestParam String email) {
        return this.pointService.getPoint(email);
    }

    //포인트 사용 내역 조회
    //기간을 입력하여 조회
    @GetMapping("/history")
    public PointHistoryResponse getPointHistory(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String email) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return new PointHistoryResponse(startDate, endDate,
                this.pointService.getPointHistory(startDateTime, endDateTime, email));
    }

    //포인트 환불
    @PostMapping("/refunds")
    public RefundPoint.Response refundPoint(@RequestBody RefundPoint.Request request) {
        return RefundPoint.Response.fromPointDto(
                this.pointService.refundPoint(request.getEmail(),
                        request.getRefundPoint()));
    }

}
