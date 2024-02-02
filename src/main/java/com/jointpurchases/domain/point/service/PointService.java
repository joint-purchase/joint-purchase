package com.jointpurchases.domain.point.service;

import com.jointpurchases.domain.point.model.dto.PointChangeDto;
import com.jointpurchases.domain.point.model.entity.MemberEntity;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import com.jointpurchases.domain.point.repository.MemberRepository;
import com.jointpurchases.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    //포인트 구매
    //이메일로 memberEntity 검색(나중에 바꿀 수 있음)
    //memberEntity로 point테이블에서 가장 최근에 거래된 내역 찾기 >> 현재 포인트 찾기
    @Transactional
    public PointChangeDto buyPoint(String email, Long money) {
        MemberEntity memberEntity = getMemberEntity(email);

        PointEntity pointEntity = getLatestPointForEntity(memberEntity);

        if (pointEntity == null) {
            return PointChangeDto.fromEntity(
                    this.pointRepository.save(PointEntity.builder()
                            .changedPoint(money)
                            .currentPoint(money)
                            .eventType("포인트 구매") //enum 타입으로 바꿀 예정
                            .memberEntity(memberEntity)
                            .createdDate(LocalDateTime.now())
                            .build()
                    ));
        } else {
            return PointChangeDto.fromEntity(this.pointRepository.save(PointEntity.builder()
                    .changedPoint(money)
                    .currentPoint(pointEntity.getCurrentPoint() + money)
                    .eventType("포인트 구매") //enum 타입으로 바꿀 예정
                    .memberEntity(memberEntity)
                    .createdDate(LocalDateTime.now())
                    .build())
            );
        }
    }


    private PointEntity getLatestPointForEntity(MemberEntity memberEntity) {
        //pageable로 가장 최근의 포인트 내역 1개만 조회
        Pageable pageable = PageRequest.of(0, 1);

        Page<PointEntity> pointEntityPage
                = this.pointRepository.findByMemberEntity(memberEntity, pageable);

        List<PointEntity> pointEntityList = pointEntityPage.getContent();

        return pointEntityList.isEmpty() ? null : pointEntityList.get(0);
    }

    private MemberEntity getMemberEntity(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다"));
    }
}
