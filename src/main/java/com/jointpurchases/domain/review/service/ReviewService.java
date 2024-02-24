package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.domain.review.exception.InvalidFileException;
import com.jointpurchases.domain.review.model.dto.CreateReviewDto;

import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
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
import java.util.Optional;
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
    public CreateReviewDto.Response createReview(long productId, String title, String contents, int rating, @Nullable List<MultipartFile> files, User user) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품이 없습니다."));
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> filePaths = new ArrayList<>();//반환할 파일 경로 목록
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<ReviewImageEntity>();//저장할 파일 엔터티 목록

        ReviewEntity newReview = ReviewEntity.builder().
                product(product).
                title(title).
                contents(contents).
                rating(rating).
                registerDate(now).
                user(user).
                build();

        reviewRepository.save(newReview);

        if(files != null){
            if(files.size() > MAXIMUM_IMAGE){
                throw new InvalidFileException();
            } else {
                for (MultipartFile file : files) {
                    ReviewImageEntity newReviewImage = new ReviewImageEntity();
                    UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
                    String filename = uuid + "-" + file.getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    file.transferTo(saveFile);

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

        return CreateReviewDto.Response.builder().
                productId(productId).
                title(title).
                contents(contents).
                rating(rating).
                registerDate(now).
                filePaths(filePaths).
                build();
    }
/*
리뷰 수정
 */
    public ModifyReviewDto.Response modifyReview(long id, String title, String contents, int rating, @Nullable List<MultipartFile> files, User user) throws IOException {
        ReviewEntity nowReview = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("리뷰가 없습니다."));
        List<ReviewImageEntity> nowReviewImageList = reviewImageRepository.findAllByReviewId(id);
        LocalDateTime now = LocalDateTime.now();
        String projectPath = System.getProperty("user.dir") + "\\image\\";//기본 폴더 경로
        ArrayList<String> filePaths = new ArrayList<>();//반환할 파일 이름 목록
        ArrayList<ReviewImageEntity> reviewImageEntityList = new ArrayList<>();//저장할 파일 엔터티 목록

        nowReview.updateReview(title, contents,rating,now);

        reviewRepository.save(nowReview);

        for(ReviewImageEntity reviewImage : nowReviewImageList){
            File deleteFile = new File(reviewImage.getFilepath());
            if(deleteFile.exists()){
                deleteFile.delete();
                reviewImageRepository.deleteById(reviewImage.getId());
            } else {
                throw new RuntimeException("파일이 존재 하지 않습니다.");
            }
        }

        if(files != null){
            if(files.size() > MAXIMUM_IMAGE){
                throw new InvalidFileException();
            } else {
                for (MultipartFile file : files) {
                    UUID uuid = UUID.randomUUID();//파일 이름 랜덤생성
                    String filename = uuid + "-" + file.getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    file.transferTo(saveFile);

                    ReviewImageEntity newReviewImage = ReviewImageEntity.builder().
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

        return ModifyReviewDto.Response.builder().
                title(title).
                contents(contents).
                rating(rating).
                registerDate(nowReview.getRegisterDate()).
                modifiedDate(now).
                filePaths(filePaths).
                build();
    }
/*
리뷰 단일 삭제
 */
    public long deleteById(long id){
        List<ReviewImageEntity> nowReviewImageList = reviewImageRepository.findAllByReviewId(id);

        for(ReviewImageEntity ReviewImage : nowReviewImageList){
            File deleteFile = new File(ReviewImage.getFilepath());
            if(deleteFile.exists()){
                deleteFile.delete();
                reviewImageRepository.deleteById(ReviewImage.getId());
            } else {
                throw new RuntimeException("파일이 존재 하지 않습니다.");
            }
            reviewImageRepository.deleteById(ReviewImage.getId());
        }

        reviewRepository.deleteById(id);
        return id;
    }
/*
상품 리뷰 전체 삭제
 */
    public long deleteAllReviewByProductId(long id){
        List<ReviewEntity> nowReviewList = reviewRepository.findAllByProductId(id);

        for(ReviewEntity reviewEntity : nowReviewList){
            deleteById(reviewEntity.getId());
        }

        return id;
    }
/*
회원 리뷰 전체 삭제
 */
    public long deleteAllReviewByUserId(User user){
        List<ReviewEntity> nowReviewList = reviewRepository.findAllByUserId(user.getId());

        for(ReviewEntity reviewEntity : nowReviewList){
            deleteById(reviewEntity.getId());
        }

        return user.getId();
    }
}

