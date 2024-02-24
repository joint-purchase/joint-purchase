package com.jointpurchases.domain.deadline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ModifyDeadlineDto {
    @Getter
    public static class Request{
        private long deadlineId;
        private int period;
        private int maximumPeople;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private long deadlineId;
        private int maximumPeople;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String status;

        public static ModifyDeadlineDto.Response response(ModifyDeadlineDto.Response response) {
            return ModifyDeadlineDto.Response.builder().
                    deadlineId(response.getDeadlineId()).
                    maximumPeople(response.getMaximumPeople()).
                    startDate(response.getStartDate()).
                    endDate(response.getEndDate()).
                    status(response.getStatus()).
                    build();
        }
    }
}
