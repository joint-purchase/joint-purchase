package com.jointpurchases.domain.point.model.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class BuyPoint {

    @Data
    public static class Request{
        private String email;
        private Long money;
    }

    @Data
    @Builder
    public static class Response{
        private String email;
        private Long changedPoint;
        private Long currentPoint;
        private String eventType;
        private LocalDateTime createdDate;

        public static Response fromPointDto(PointChangeDto pointDto){
            return Response.builder()
                    .email(pointDto.getEmail())
                    .changedPoint(pointDto.getChangedPoint())
                    .currentPoint(pointDto.getCurrentPoint())
                    .eventType(pointDto.getEventType())
                    .createdDate(pointDto.getCreatedDate())
                    .build();
        }
    }
}
