package com.jointpurchases.domain.review.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.review.model.dto.GetReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewReadService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    /*
리뷰 조회
 */
    public GetReviewDto.Response getOneById(long id){
        ReviewEntity review = reviewRepository.findById(id).get();
        List<ReviewImageEntity> getReviewImageList = reviewImageRepository.findAllByReviewId(id);
        ArrayList<String> filePaths = new ArrayList<>();

        for(ReviewImageEntity gerReviewImage : getReviewImageList){
            filePaths.add(gerReviewImage.getFilepath());
        }

        return GetReviewDto.Response.builder().
                title(review.getTitle()).
                contents(review.getContents()).
                rating(review.getRating()).
                registerDate(review.getRegisterDate()).
                modifiedDate(review.getModifiedDate()).
                filePaths(filePaths).
                build();
    }
/*
상품 리뷰 조회
 */
    public List<GetReviewDto.Response> getAllByProductId(long id){
        List<GetReviewDto.Response> getReviewDtoResponseList = new ArrayList<>();
        List<ReviewEntity> getReviewList = reviewRepository.findAllByProductId(id);

        for(ReviewEntity review : getReviewList){
            List<ReviewImageEntity> getReviewImageList = reviewImageRepository.findAllByReviewId(review.getId());
            ArrayList<String> filePaths = new ArrayList<>();

            for(ReviewImageEntity ReviewImage : getReviewImageList){
                filePaths.add(ReviewImage.getFilepath());
            }

            GetReviewDto.Response response = GetReviewDto.Response.builder().
                    title(review.getTitle()).
                    contents(review.getContents()).
                    rating(review.getRating()).
                    registerDate(review.getRegisterDate()).
                    modifiedDate(review.getModifiedDate()).
                    filePaths(filePaths).
                    build();

            getReviewDtoResponseList.add(response);
        }

        return getReviewDtoResponseList;
    }
/*
유저 리뷰 조회
 */
    public List<GetReviewDto.Response> getAllByUserId(User user){
        List<GetReviewDto.Response> getReviewDtoResponseList = new ArrayList<>();
        List<ReviewEntity> getReviewList = reviewRepository.findAllByProductId(user.getId());

        for(ReviewEntity review : getReviewList){
            List<ReviewImageEntity> getReviewImageList = reviewImageRepository.findAllByReviewId(review.getId());
            ArrayList<String> filePaths = new ArrayList<>();

            for(ReviewImageEntity ReviewImage : getReviewImageList){
                filePaths.add(ReviewImage.getFilepath());
            }

            GetReviewDto.Response response = GetReviewDto.Response.builder().
                    title(review.getTitle()).
                    contents(review.getContents()).
                    rating(review.getRating()).
                    registerDate(review.getRegisterDate()).
                    modifiedDate(review.getModifiedDate()).
                    filePaths(filePaths).
                    build();

            getReviewDtoResponseList.add(response);
        }

        return getReviewDtoResponseList;
    }

}
