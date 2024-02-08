package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.review.exception.InvalidFileException;
import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    /*
    리뷰 작성
     */
    public CreateReviewDto.Response createReview(String title, String contents, int rating, List<MultipartFile> files) throws IOException {
        if(files.size() > 5){
            throw new InvalidFileException();
        }
        LocalDateTime now = LocalDateTime.now();
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> fileNames = new ArrayList<>();//반환할 파일 이름 목록
        ReviewEntity newReview = new ReviewEntity();
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<ReviewImageEntity>();//저장할 파일 엔터티 목록

        newReview.setTitle(title);
        newReview.setContents(contents);
        newReview.setRating(rating);
        newReview.setRegisterDate(LocalDateTime.now());
        reviewRepository.save(newReview);

        for (int i = 0; i < files.size(); i++) {
            ReviewImageEntity newReviewImage = new ReviewImageEntity();
            UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
            String filename = uuid + "-" + files.get(i).getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            files.get(i).transferTo(saveFile);
            fileNames.add(filename);
            newReviewImage.setFilename(filename);
            newReviewImage.setFilepath(projectPath + filename);
            newReviewImage.setUploadDate(now);
            newReviewImage.setReview(newReview);
            reviewImageEntityList.add(newReviewImage);
        }

        reviewImageRepository.saveAll(reviewImageEntityList);

        CreateReviewDto.Response response = new CreateReviewDto.Response(title, contents, rating, now, fileNames);

        return CreateReviewDto.Response.response(response);
    }
/*
리뷰 수정
 */
    public ModifyReviewDto.Response modifyReview(int id, String title, String contents, int rating, List<MultipartFile> files) throws IOException {
        if(files.size() > 5){
            throw new InvalidFileException();
        }
        ReviewEntity nowReview = reviewRepository.getById(id);
        List<ReviewImageEntity> nowReviewImageList = reviewImageRepository.getAllByReviewId(id);
        LocalDateTime now = LocalDateTime.now();
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> fileNames = new ArrayList<>();//반환할 파일 이름 목록
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<ReviewImageEntity>();//저장할 파일 엔터티 목록

        nowReview.setTitle(title);
        nowReview.setContents(contents);
        nowReview.setRating(rating);
        nowReview.setModifiedDate(now);
        reviewRepository.save(nowReview);

        reviewRepository.save(nowReview);

        for(int i = 0; i < nowReviewImageList.size();i++){
            File deleteFile = new File(nowReviewImageList.get(i).getFilepath());
            if(deleteFile.exists()){
                deleteFile.delete();
                reviewImageRepository.deleteById(nowReviewImageList.get(i).getId());
            } else {
                throw new RuntimeException("파일이 존재 하지 않습니다.");
            }
        }

        for (int i = 0; i < files.size(); i++) {
            ReviewImageEntity newReviewImage = new ReviewImageEntity();
            UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
            String filename = uuid + "-" + files.get(i).getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            files.get(i).transferTo(saveFile);
            fileNames.add(filename);
            newReviewImage.setFilename(filename);
            newReviewImage.setFilepath(projectPath + filename);
            newReviewImage.setUploadDate(now);
            newReviewImage.setReview(nowReview);
            reviewImageEntityList.add(newReviewImage);
        }

        reviewImageRepository.saveAll(reviewImageEntityList);

        ModifyReviewDto.Response response = new ModifyReviewDto.Response(title, contents, rating, nowReview.getRegisterDate() ,now, fileNames);

        return ModifyReviewDto.Response.response(response);
    }
/*
리뷰 조회
 */
    public GetReviewDto.Response getOneById(int id){
        ReviewEntity getReview = reviewRepository.getById(id);
        List<ReviewImageEntity> getReviewImageList = reviewImageRepository.getAllByReviewId(id);
        ArrayList<String> fileNames = new ArrayList<>();

        for(int i = 0; i < getReviewImageList.size();i++){
            fileNames.add(getReviewImageList.get(i).getFilename());
        }

        GetReviewDto.Response response = new GetReviewDto.Response(getReview.getTitle(),
                getReview.getContents(), getReview.getRating(), getReview.getRegisterDate(),
                getReview.getModifiedDate(), fileNames);
        return response;
    }

}

