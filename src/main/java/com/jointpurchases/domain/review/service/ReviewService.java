package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public CreateReviewDto.Response createReview(String title, String contents, int rating, List<MultipartFile> files) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//현재 시간
        String projectPath = System.getProperty("user.dir") + "\\image";//기본 폴더 경로
        ArrayList<String> fileNames = new ArrayList<>();//반환할 파일 이름 목록
        UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
        ReviewEntity newReview = new ReviewEntity();
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<ReviewImageEntity>();//저장할 파일 엔터티 목록

        newReview.setTitle(title);
        newReview.setContents(contents);
        newReview.setRating(rating);
        newReview.setRegisterDate(now);
        reviewRepository.save(newReview);

        for (int i = 0; i < files.size(); i++) {
            ReviewImageEntity newReviewImage = new ReviewImageEntity();
            String filename = uuid + "-" + files.get(i).getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            files.get(i).transferTo(saveFile);
            newReviewImage.setFilename(filename);
            fileNames.add(filename);
            newReviewImage.setFilepath("/image/" + filename);
            newReviewImage.setUploadDate(now);
            newReviewImage.setReview(newReview);
            reviewImageEntityList.add(newReviewImage);
        }

        reviewImageRepository.saveAll(reviewImageEntityList);

        CreateReviewDto.Response response = new CreateReviewDto.Response(title, contents, rating, now, fileNames);

        return CreateReviewDto.Response.response(response);
    }
}

