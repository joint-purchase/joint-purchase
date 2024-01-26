package com.jointpurchases.domain.point.service;

import com.jointpurchases.domain.point.model.Dto.PointChangeDto;
import com.jointpurchases.domain.point.model.Dto.SearchPoint;
import com.jointpurchases.domain.point.model.entity.MemberEntity;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import com.jointpurchases.domain.point.repository.MemberRepository;
import com.jointpurchases.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    //포인트 구매
    //이메일로 memberEntity 검색(나중에 바꿀 수 있음)
    //memberEntity로 point테이블에서 가장 최근에 거래된 내역 찾기 >> 현재 포인트 찾기
    public PointChangeDto buyPoint(String email, Long money) {
        MemberEntity memberEntity = getMemberEntity(email);

        PointEntity pointEntity = getPointEntity(memberEntity);

        return PointChangeDto.fromEntity(
                this.pointRepository.save(PointEntity.builder()
                        .changedPoint(money)
                        .currentPoint(pointEntity.getCurrentPoint() + money)
                        .eventType("포인트 구매") //enum 타입으로 바꿀 예정
                        .memberEntity(memberEntity)
                        .createdDate(LocalDateTime.now())
                        .build()
                ));
    }

    //현재 포인트 조회
    //이메일로 memberEntity 검색
    public SearchPoint searchPoint(String email){
        MemberEntity memberEntity = getMemberEntity(email);

        PointEntity pointEntity = getPointEntity(memberEntity);

        return SearchPoint.builder()
                .currentPoint(pointEntity.getCurrentPoint())
                .build();
    }

    private PointEntity getPointEntity(MemberEntity memberEntity) {
        PointEntity pointEntity = this.pointRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new RuntimeException("포인트 내역이 없습니다."));
        return pointEntity;
    }

    private MemberEntity getMemberEntity(String email) {
        MemberEntity memberEntity = this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다"));
        return memberEntity;
    }
}
