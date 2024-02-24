package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.service.ReviewService;
import com.jointpurchases.global.tool.LoginUser;
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

dto 구성 : 상품ID(id), 제목(title), 내용(contents), 별점(rating) ,사진 파일(multipartfile)
 */
    @PostMapping
    public CreateReviewDto.Response createReview(@RequestPart(value = "dto") CreateReviewDto.Request request, @RequestPart(value = "files", required = false) List<MultipartFile> files, @LoginUser final User user) throws IOException {
       return reviewService.createReview(request.getProductId(), request.getTitle(), request.getContents(), request.getRating(), files, user);
    }
/*
리뷰 수정
dto 구성 : 리뷰ID(id), 제목(title), 내용(contents), 별점(rating),사진 파일(multipartfile)
 */
    @PutMapping
    public ModifyReviewDto.Response modifyReview(@RequestPart(value = "dto", required = false) ModifyReviewDto.Request request, @RequestPart(value = "files", required = false) List<MultipartFile> files, @LoginUser final User user) throws IOException {
        return reviewService.modifyReview(request.getId(), request.getTitle(), request.getContents(), request.getRating(), files, user);
    }
/*
리뷰 단일 삭제
 */
    @DeleteMapping
    public long deleteReview(@RequestParam(value = "id") long id){
        return reviewService.deleteById(id);
    }
/*
상품 리뷰 전체 삭제
 */
    @DeleteMapping("/product")
    public long deleteAllReviewByProductId(@RequestParam(value = "id") long id){
        return reviewService.deleteAllReviewByProductId(id);
    }
/*
유저 리뷰 전체 삭제
 */
    @DeleteMapping("/user")
    public long deleteAllReviewByUserId(@LoginUser final User user){
        return reviewService.deleteAllReviewByUserId(user);
    }
}