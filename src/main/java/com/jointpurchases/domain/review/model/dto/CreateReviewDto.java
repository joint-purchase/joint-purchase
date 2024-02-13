package com.jointpurchases.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateReviewDto {
/*
리뷰 작성 요청값
 */
    @Getter
    public static class Request {
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
        private ArrayList<String> fileNames;

        public static Response response(Response response){
            return Response.builder().
                title(response.getTitle()).
                contents(response.getContents()).
                rating(response.getRating()).
                registerDate(response.getRegisterDate()).
                fileNames(response.getFileNames()).
                build();
        }
    }

}
