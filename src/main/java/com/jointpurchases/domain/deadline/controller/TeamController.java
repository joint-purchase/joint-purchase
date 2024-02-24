package com.jointpurchases.domain.deadline.controller;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.deadline.model.dto.AddTeamMemberDto;
import com.jointpurchases.domain.deadline.model.dto.CreateTeamDto;
import com.jointpurchases.domain.deadline.model.dto.ModifyDeadlineDto;
import com.jointpurchases.domain.deadline.service.TeamService;
import com.jointpurchases.global.tool.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;
/*
팀 생성
 */
    @PostMapping
    public CreateTeamDto.Response createTeam(@RequestPart(name = "dto") CreateTeamDto.Request request, @LoginUser final User user){
        return teamService.createTeam(request.getDeadlineId(), user);
    }
/*
팀 멤버 추가
 */
    @PutMapping
    public AddTeamMemberDto.Response addTeamMember(@RequestPart(name = "dto") AddTeamMemberDto.Request request, @LoginUser final User user){
        return teamService.addTeamMember(request.getTeamId(), user);
    }
/*
팀 멤버 제거 및 팀 멤버가 0일때 팀 삭제
 */
    @DeleteMapping
    public String deleteTeamMember(@RequestParam(name = "id") long id, @LoginUser final User user){
        return teamService.deleteTeamMember(id, user);
    }

}
