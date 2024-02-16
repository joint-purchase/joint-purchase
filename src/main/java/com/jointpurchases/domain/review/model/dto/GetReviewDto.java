package com.jointpurchases.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetReviewDto {
    @Getter
    public static class Request {
        private int id;
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
        private ArrayList<String> filePaths;

        public static Response response(Response response){
            return Response.builder().
                    title(response.getTitle()).
                    contents(response.getContents()).
                    rating(response.rating).
                    registerDate(response.getRegisterDate()).
                    modifiedDate(response.getModifiedDate()).
                    filePaths(response.getFilePaths()).
                    build();
        }
    }
}
