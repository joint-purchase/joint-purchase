package com.jointpurchases.domain.point.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.point.model.dto.BuyPoint;
import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.model.dto.PointHistoryResponse;
import com.jointpurchases.domain.point.model.dto.RefundPoint;
import com.jointpurchases.domain.point.service.PointService;
import com.jointpurchases.global.tool.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    //현금으로 포인트 구매
    @PostMapping
    public BuyPoint.Response buyPoint(@RequestBody BuyPoint.Request request,
                                      @LoginUser User user) {
        return BuyPoint.Response.fromPointDto(
                this.pointService.buyPoint(user, request.getMoney())
        );
    }

    //현재 포인트 조회
    @GetMapping
    public GetPoint getCurrentPoint(@LoginUser User user) {
        return this.pointService.getPoint(user);
    }

    //포인트 사용 내역 조회
    //기간을 입력하여 조회
    @GetMapping("/history")
    public PointHistoryResponse getPointHistory(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @LoginUser User user) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return new PointHistoryResponse(startDate, endDate,
                this.pointService.getPointHistory(startDateTime, endDateTime, user));
    }

    //포인트 환불
    @PostMapping("/refunds")
    public RefundPoint.Response refundPoint(@RequestBody RefundPoint.Request request,
                                            @LoginUser User user) {
        return RefundPoint.Response.fromPointDto(
                this.pointService.refundPoint(user,
                        request.getRefundPoint()));
    }

}
