package com.jointpurchases.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModifyReviewDto {
    @Getter
    public static class Request {
        private int id;
        private String title;
        private String contents;
        private int rating;
    }
    /*
    리뷰 작성 반환값
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private String title;
        private String contents;
        private int rating;
        private LocalDateTime registerDate;
        private LocalDateTime modifiedDate;
        private ArrayList<String> fileNames;

        public static ModifyReviewDto.Response response(ModifyReviewDto.Response response){
            return ModifyReviewDto.Response.builder().
                    title(response.getTitle()).
                    contents(response.getContents()).
                    rating(response.getRating()).
                    registerDate(response.getRegisterDate()).
                    modifiedDate(response.getModifiedDate()).
                    fileNames(response.getFileNames()).
                    build();
        }
    }
}
