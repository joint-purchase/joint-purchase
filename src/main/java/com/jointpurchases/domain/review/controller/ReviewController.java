package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
/*
리뷰 작성
dto 구성 : 제목(title), 내용(contents), 별점(rating) ,사진파일(multipartfile)
 */
    @PostMapping
    public CreateReviewDto.Response createReview(@RequestPart(value = "dto") CreateReviewDto.Request request,@RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
       return reviewService.createReview(request.getTitle(), request.getContents(), request.getRating(), files);
    }
}