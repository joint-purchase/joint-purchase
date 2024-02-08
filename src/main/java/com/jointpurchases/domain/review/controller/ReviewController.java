package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/review")
public class ReviewController {
    private final ReviewService reviewService;
/*
리뷰 작성
dto 구성 : 제목(title), 내용(contents), 별점(rating) ,사진 파일(multipartfile)
 */
    @PostMapping
    public CreateReviewDto.Response createReview(@RequestPart(value = "dto") CreateReviewDto.Request request,@RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
       return reviewService.createReview(request.getTitle(), request.getContents(), request.getRating(), files);
    }
/*
리뷰 수정
dto 구성 : 리뷰ID(id), 제목(title), 내용(contents), 별점(rating),사진 파일(multipartfile)
 */
    @PutMapping
    public ModifyReviewDto.Response modifyReview(@RequestPart(value = "dto") ModifyReviewDto.Request request, @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
        return reviewService.modifyReview(request.getId(), request.getTitle(), request.getContents(), request.getRating(), files);
    }
/*
리뷰 조회
dto 구성 : 리뷰ID(id)
 */
    @GetMapping
    public GetReviewDto.Response getOneById(@RequestPart(value = "dto") GetReviewDto.Request request){
        return reviewService.getOneById(request.getId());
    }

}