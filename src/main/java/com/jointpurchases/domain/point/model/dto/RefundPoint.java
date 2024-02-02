package com.jointpurchases.domain.point.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class RefundPoint {

    @Data
    public static class Request{
        private String email;
        private Long refundPoint;
    }

    @Data
    @Builder
    public static class Response{
        private String email;
        private Long changedPoint;
        private Long currentPoint;
        private String eventType;
        private LocalDateTime createdDate;

        public static Response fromPointDto(PointChangeDto pointChangeDto){
            return Response.builder()
                    .email(pointChangeDto.getEmail())
                    .changedPoint(pointChangeDto.getChangedPoint())
                    .currentPoint(pointChangeDto.getCurrentPoint())
                    .eventType(pointChangeDto.getEventType())
                    .createdDate(pointChangeDto.getCreatedDate())
                    .build();
        }
    }
}
