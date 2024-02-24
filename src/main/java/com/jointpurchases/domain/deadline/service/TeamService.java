package com.jointpurchases.domain.deadline.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.deadline.model.dto.AddTeamMemberDto;
import com.jointpurchases.domain.deadline.model.dto.CreateTeamDto;
import com.jointpurchases.domain.deadline.model.entity.DeadlineEntity;
import com.jointpurchases.domain.deadline.model.entity.TeamEntity;
import com.jointpurchases.domain.deadline.repository.TeamRepository;
import com.jointpurchases.domain.deadline.repository.DeadlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final DeadlineRepository deadlineRepository;
    private final TeamRepository teamRepository;
    private final DeadlineService deadlineService;
/*
공동 구매팀 생성
 */
    public CreateTeamDto.Response createTeam(long deadlineId, User user){
        DeadlineEntity deadline = deadlineRepository.findById(deadlineId).orElseThrow(() -> new RuntimeException("마감기간이 존재하지 않습니다."));

        TeamEntity team = TeamEntity.builder().
                deadline(deadline).
                user(user).
                build();

        teamRepository.save(team);

        return CreateTeamDto.Response.builder().
                teamId(team.getId()).
                userId(user.getId()).
                userEmail(user.getEmail()).
                build();
    }
/*
공동 구매팀 인원 추가
 */
    public AddTeamMemberDto.Response addTeamMember(long teamId, User user){
        TeamEntity nowTeam = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("팀이 존재하지 않습니다."));
        nowTeam.addTeamMember(user);
/*
마감 인원과 주문 인원 체크 후 바로 주문
 */
        if(nowTeam.getDeadline().getMaximumPeople() == nowTeam.getUser().size()){
            deadlineService.callOrder(nowTeam.getDeadline(), nowTeam);
            deadlineService.cancelMail(nowTeam.getId());
            deadlineService.deleteDeadline(nowTeam.getDeadline().getId());
            log.info(nowTeam.getId()+"주문 완료");

            return AddTeamMemberDto.Response.builder().
                    teamId(nowTeam.getDeadline().getId()).
                    build();
        } else{
            teamRepository.save(nowTeam);

            return AddTeamMemberDto.Response.builder().
                    teamId(teamId).
                    userId(user.getId()).
                    userEmail(user.getEmail()).
                    build();
        }
    }
/*
팀 인원 삭제
 */
    public String deleteTeamMember(long teamId, User user){
        TeamEntity nowTeam = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("팀이 존재하지 않습니다."));

        nowTeam.getUser().removeIf((item) -> item.getId().equals(user.getId()));

        int countMember = teamRepository.save(nowTeam).getUser().size();

        if(countMember == 0){
            teamRepository.deleteById(nowTeam.getId());
            return teamId+"팀 삭제";
        }

        return user.getEmail()+"삭제";
    }
}
