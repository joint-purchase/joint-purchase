package com.jointpurchases.domain.deadline.model.dto;

import com.jointpurchases.domain.auth.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CreateTeamDto {
/*
팀 추가
 */
    @Getter
    public static class Request {
        private long deadlineId;
        private User user;
    }
/*
팀 추가 반환값
*/
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private long teamId;
        private long userId;
        private String userEmail;


        public static Response response(Response response){
            return Response.builder().
                    teamId(response.getTeamId()).
                    userId(response.getUserId()).
                    userEmail(response.getUserEmail()).
                    build();
        }
    }
}
