package com.jointpurchases.domain.point.service;

import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.model.dto.PointChangeDto;
import com.jointpurchases.domain.point.model.dto.PointHistory;
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
import java.util.stream.Collectors;

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

    //현재 포인트 조회
    //이메일로 memberEntity 검색
    public GetPoint getPoint(String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        PointEntity pointEntity = getLatestPointForEntity(memberEntity);

        if (pointEntity == null) {
            throw new RuntimeException("포인트 내역이 없습니다.");
        }

        return GetPoint.builder()
                .currentPoint(pointEntity.getCurrentPoint())
                .build();
    }

    //특정 기간 사이의 포인드 사용 내역 조회
    public List<PointHistory> getPointHistory(LocalDateTime startDateTime,
                                              LocalDateTime endDateTime, String email) {
        MemberEntity memberEntity = getMemberEntity(email);

        List<PointEntity> pointEntityList =
                this.pointRepository.findAllByMemberEntityAndCreatedDateBetween(
                        memberEntity, startDateTime, endDateTime);

        return pointEntityList.stream()
                .map(e -> new PointHistory(e.getId(), e.getChangedPoint(),
                        e.getCurrentPoint(), e.getEventType(), e.getCreatedDate()))
                .collect(Collectors.toList());
    }

    //포인트 환불
    //pointEntity == null인 경우(거래가 한번도 없는 경우)와
    //환불 포인드가 잔액 포인트 보다 많은 경우 예외 발생
    @Transactional
    public PointChangeDto refundPoint(String email, Long refundPoint) {
        MemberEntity memberEntity = getMemberEntity(email);

        PointEntity pointEntity = getLatestPointForEntity(memberEntity);

        if (pointEntity == null || pointEntity.getCurrentPoint() < refundPoint) {
            throw new RuntimeException("환불할 포인트 잔액이 부족합니다.");
        } else {
            Long currentPoint = pointEntity.getCurrentPoint() - refundPoint;

            return PointChangeDto.fromEntity(this.pointRepository.save(PointEntity.builder()
                    .memberEntity(memberEntity)
                    .changedPoint(refundPoint * -1)
                    .currentPoint(currentPoint)
                    .eventType("포인트 환불")
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
