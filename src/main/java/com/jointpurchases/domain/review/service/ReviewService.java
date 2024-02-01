package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.review.model.Review;
import com.jointpurchases.domain.review.model.ReviewImage;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
//생성자
    public ReviewService(ReviewRepository reviewRepository, ReviewImageRepository reviewImageRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
    }
/*
리뷰 작성
 */
    public void createReview(String title, String contents, int rating, List<MultipartFile> files) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//현재 시간
        String projectPath = System.getProperty("user.dir") + "\\image";//기본 폴더 경로
        UUID uuid = UUID.randomUUID();
        Review newReview = new Review();
        ReviewImage newReviewImage = new ReviewImage();

            newReview.setTitle(title);
            newReview.setContents(contents);
            newReview.setRating(rating);
            newReview.setRegisterDate(now);
            reviewRepository.save(newReview);

        for(int i = 0 ; i < files.size();i++){
            String filename = uuid +"-"+ files.get(i).getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            files.get(i).transferTo(saveFile);
            newReviewImage.setFilename(filename);
            newReviewImage.setFilepath("/image/" + filename);
            newReviewImage.setUploadDate(now);
            newReviewImage.setReview(newReview);
            reviewImageRepository.save(newReviewImage);
        }
    }
/*
리뷰 수정
 */
    public void modifyReview(int id, String title, String contents, int rating, List<MultipartFile> files) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//현재 시간
        Review modifyReview = reviewRepository.getOneById(id);
        String projectPath = System.getProperty("user.dir") + "\\image"; //기본 폴더 경로
        UUID uuid = UUID.randomUUID();
        Review newReview = new Review();
        ReviewImage newReviewImage = new ReviewImage();

            modifyReview.setTitle(title);
            modifyReview.setContents(contents);
            modifyReview.setRating(rating);
            modifyReview.setModifiedDate(now);
            reviewRepository.save(modifyReview);

        for(int i = 0 ; i < files.size();i++){
            String filename = uuid +"-"+ files.get(i).getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            files.get(i).transferTo(saveFile);
            newReviewImage.setFilename(filename);
            newReviewImage.setFilepath("/image/" + filename);
            newReviewImage.setUploadDate(now);
            newReviewImage.setReview(newReview);
            reviewImageRepository.save(newReviewImage);
        }
    }
/*
리뷰 삭제
 */
    public void deleteReview(int id){
        reviewRepository.deleteById(id);
    }
}