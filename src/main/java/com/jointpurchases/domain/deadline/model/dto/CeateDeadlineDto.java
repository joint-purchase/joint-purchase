package com.jointpurchases.domain.deadline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CeateDeadlineDto {

    @Getter
    public static class Request{
        private long productId;
        private int period;
        private int maximumPeople;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private long productId;
        private int maximumPeople;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String status;

        public static Response response(Response response) {
            return Response.builder().
                    maximumPeople(response.getMaximumPeople()).
                    startDate(response.getStartDate()).
                    endDate(response.getEndDate()).
                    status(response.getStatus()).
                    build();
        }
    }
}
