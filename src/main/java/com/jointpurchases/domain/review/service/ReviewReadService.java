package com.jointpurchases.domain.review.service;

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
        ReviewEntity getReview = reviewRepository.getById(id);
        List<ReviewImageEntity> getReviewImageList = reviewImageRepository.getAllByReviewId(id);
        ArrayList<String> filePaths = new ArrayList<>();

        for(int i = 0; i < getReviewImageList.size();i++){
            filePaths.add(getReviewImageList.get(i).getFilepath());
        }

        return GetReviewDto.Response.builder().
                title(getReview.getTitle()).
                contents(getReview.getContents()).
                rating(getReview.getRating()).
                registerDate(getReview.getRegisterDate()).
                modifiedDate(getReview.getModifiedDate()).
                filePaths(filePaths).
                build();
    }

    public List<GetReviewDto.Response> getAllByProductId(long id){
        List<GetReviewDto.Response> getReviewDtoResponseList = new ArrayList<>();
        List<ReviewEntity> getReviewList = reviewRepository.getAllByProductId(id);

        for(int i = 0; i < getReviewList.size();i++){
            List<ReviewImageEntity> getReviewImageList = reviewImageRepository.getAllByReviewId(getReviewList.get(i).getId());
            ArrayList<String> filePaths = new ArrayList<>();

            for(int j = 0; j < getReviewImageList.size();j++){
                filePaths.add(getReviewImageList.get(i).getFilepath());
            }

            GetReviewDto.Response response = GetReviewDto.Response.builder().
                    title(getReviewList.get(i).getTitle()).
                    contents(getReviewList.get(i).getContents()).
                    rating(getReviewList.get(i).getRating()).
                    registerDate(getReviewList.get(i).getRegisterDate()).
                    modifiedDate(getReviewList.get(i).getModifiedDate()).
                    filePaths(filePaths).
                    build();

            getReviewDtoResponseList.add(response);
        }

        return getReviewDtoResponseList;
    }
}
