package com.jointpurchases.domain.deadline.model.dto;

import com.jointpurchases.domain.auth.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AddTeamMemberDto {

    /*
팀 멤버 추가
 */
    @Getter
    public static class Request {
        private long teamId;
        private User user;
    }
    /*
    팀 멤버 추가 반환값
    */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private long teamId;
        private long userId;
        private String userEmail;


        public static CreateTeamDto.Response response(CreateTeamDto.Response response){
            return CreateTeamDto.Response.builder().
                    teamId(response.getTeamId()).
                    userId(response.getUserId()).
                    userEmail(response.getUserEmail()).
                    build();
        }
    }
}
