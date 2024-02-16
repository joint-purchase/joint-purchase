package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.service.ReviewReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/review")
public class ReviewReadController {
    private final ReviewReadService reviewReadService;
    /*
    리뷰 조회
    dto 구성 : 리뷰ID(id)
     */
    @GetMapping
    public GetReviewDto.Response getOneById(@RequestPart(value = "dto") GetReviewDto.Request request){
        return reviewReadService.getOneById(request.getId());
    }
    @GetMapping("/product")
    public List<GetReviewDto.Response> getAllByProductId(@RequestPart(value = "dto") GetReviewDto.Request request){
        return reviewReadService.getAllByProductId(request.getId());
    }
}
