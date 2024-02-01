package com.jointpurchases.domain.review.controller;

import com.jointpurchases.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    void createReview(HttpServletRequest request, List<MultipartFile> files) throws IOException {
        int rating = Integer.parseInt(request.getParameter("rating"));
        reviewService.createReview(request.getParameter("title"), request.getParameter("contents"), rating, files);
    }
    @PostMapping("/review/modify")
    void modifyReview(@RequestParam("id") int id, HttpServletRequest request, List<MultipartFile> files) throws IOException {
        int rating = Integer.parseInt(request.getParameter("rating"));
        reviewService.modifyReview(id, request.getParameter("title"), request.getParameter("contents"), rating, files);
    }

    @DeleteMapping("/review")
    void deleteReview(@RequestParam("id") int id){
        reviewService.deleteReview(id);
    }
}