package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.service.ReviewReadService;
import com.jointpurchases.global.tool.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/review/read")
public class ReviewReadController {
    private final ReviewReadService reviewReadService;
    /*
    리뷰 조회
    dto 구성 : 리뷰ID(id)
     */
    @GetMapping("{id}")
    public GetReviewDto.Response getOneById(@PathVariable("id") long id){
        return reviewReadService.getOneById(id);
    }
/*
상품 리뷰 전체
 */
    @GetMapping("/product/{id}")
    public List<GetReviewDto.Response> getAllByProductId(@PathVariable("id") long id){
        return reviewReadService.getAllByProductId(id);
    }
/*
유저 리뷰 전체 조회
 */
    @GetMapping("/user")
    public List<GetReviewDto.Response> getAllByUserId(@LoginUser User user){
        return reviewReadService.getAllByUserId(user);
    }

}
