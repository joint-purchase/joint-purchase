package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.review.exception.InvalidFileException;
import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ProductEntity;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import com.jointpurchases.domain.review.repository.ProductRepository;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ProductRepository productRepository;
    private static final int MAXIMUM_IMAGE = 5;
    /*
    리뷰 작성
     */
    public CreateReviewDto.Response createReview(long productId, String title, String contents, int rating, @Nullable List<MultipartFile> files) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        ProductEntity product = productRepository.getById(productId);
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> filePaths = new ArrayList<>();//반환할 파일 경로 목록
        ReviewEntity newReview = new ReviewEntity();
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<ReviewImageEntity>();//저장할 파일 엔터티 목록

        newReview = ReviewEntity.builder().
                product(product).
                title(title).
                contents(contents).
                rating(rating).
                registerDate(now).
                build();

        reviewRepository.save(newReview);

        if(files != null){
            if(files.size() > MAXIMUM_IMAGE){
                throw new InvalidFileException();
            } else {
                for (int i = 0; i < files.size(); i++) {
                    ReviewImageEntity newReviewImage = new ReviewImageEntity();
                    UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
                    String filename = uuid + "-" + files.get(i).getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    files.get(i).transferTo(saveFile);

                    newReviewImage = ReviewImageEntity.builder().
                            filename(filename).
                            filepath(projectPath + filename).
                            uploadDate(now).
                            review(newReview).
                            build();

                    reviewImageEntityList.add(newReviewImage);
                    filePaths.add(projectPath + filename);
                }

                reviewImageRepository.saveAll(reviewImageEntityList);
            }
        }

        CreateReviewDto.Response response = CreateReviewDto.Response.builder().
                productId(productId).
                title(title).
                contents(contents).
                rating(rating).
                registerDate(now).
                filePaths(filePaths).
                build();

        return CreateReviewDto.Response.response(response);
    }
/*
리뷰 수정
 */
    public ModifyReviewDto.Response modifyReview(long id, String title, String contents, int rating, @Nullable List<MultipartFile> files) throws IOException {
        ReviewEntity nowReview = reviewRepository.getById(id);
        List<ReviewImageEntity> nowReviewImageList = reviewImageRepository.getAllByReviewId(id);
        LocalDateTime now = LocalDateTime.now();
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> filePaths = new ArrayList<>();//반환할 파일 이름 목록
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<>();//저장할 파일 엔터티 목록

        nowReview.setTitle(title);
        nowReview.setContents(contents);
        nowReview.setRating(rating);
        nowReview.setModifiedDate(now);

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

        if(files != null){
            if(files.size() > MAXIMUM_IMAGE){
                throw new InvalidFileException();
            } else {
                for (int i = 0; i < files.size(); i++) {
                    ReviewImageEntity newReviewImage = new ReviewImageEntity();
                    UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
                    String filename = uuid + "-" + files.get(i).getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    files.get(i).transferTo(saveFile);

                    newReviewImage = ReviewImageEntity.builder().
                            filename(filename).
                            filepath(projectPath + filename).
                            uploadDate(now).
                            review(nowReview).
                            build();

                    reviewImageEntityList.add(newReviewImage);
                    filePaths.add(projectPath + filename);
                }
                reviewImageRepository.saveAll(reviewImageEntityList);
            }
        }

        ModifyReviewDto.Response response = ModifyReviewDto.Response.builder().
                title(title).
                contents(contents).
                rating(rating).
                registerDate(nowReview.getRegisterDate()).
                modifiedDate(now).
                filePaths(filePaths).
                build();

        return ModifyReviewDto.Response.response(response);
    }
/*
리뷰 삭제
 */
    public long deleteById(long id){
        List<ReviewImageEntity> nowReviewImageList = reviewImageRepository.getAllByReviewId(id);

        for(int i = 0; i < nowReviewImageList.size();i++){
            File deleteFile = new File(nowReviewImageList.get(i).getFilepath());
            if(deleteFile.exists()){
                deleteFile.delete();
                reviewImageRepository.deleteById(nowReviewImageList.get(i).getId());
            } else {
                throw new RuntimeException("파일이 존재 하지 않습니다.");
            }
        }

        reviewRepository.deleteById(id);
        return id;
    }
}

