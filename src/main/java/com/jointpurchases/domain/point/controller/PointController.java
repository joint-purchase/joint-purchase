package com.jointpurchases.domain.point.controller;

import com.jointpurchases.domain.point.model.dto.BuyPoint;
import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public GetPoint getCurrentPoint(@RequestParam String email) {
        return this.pointService.getPoint(email);
    }

}
