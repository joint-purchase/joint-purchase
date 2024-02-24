package com.jointpurchases.domain.point.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

public class BuyPoint {

    @Getter
    public static class Request {
        private Integer money;
    }

    @Getter
    @Builder
    public static class Response {
        private String email;
        private Integer changedPoint;
        private Integer currentPoint;
        private String eventType;
        private LocalDateTime createdDate;

        public static Response fromPointDto(PointChangeDto pointChangeDto) {
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
