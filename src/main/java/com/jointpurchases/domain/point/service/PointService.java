package com.jointpurchases.domain.point.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.point.exception.PointException;
import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.model.dto.PointChangeDto;
import com.jointpurchases.domain.point.model.dto.PointHistory;
import com.jointpurchases.domain.point.model.entity.PointEntity;
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

import static com.jointpurchases.global.exception.ErrorCode.NOT_ENOUGH_POINT_BALANCE;
import static com.jointpurchases.global.exception.ErrorCode.NO_POINT_USING_HISTORY;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    //포인트 구매
    //이메일로 memberEntity 검색(나중에 바꿀 수 있음)
    //memberEntity로 point테이블에서 가장 최근에 거래된 내역 찾기 >> 현재 포인트 찾기
    @Transactional
    public PointChangeDto buyPoint(User userEntity, Integer money) {
        PointEntity pointEntity = getLatestPointForEntity(userEntity);

        if (pointEntity == null) {
            return PointChangeDto.fromEntity(
                    this.pointRepository.save(PointEntity.builder()
                            .changedPoint(money)
                            .currentPoint(money)
                            .eventType("포인트 구매") //enum 타입으로 바꿀 예정
                            .userEntity(userEntity)
                            .createdDate(LocalDateTime.now())
                            .build()
                    ));
        } else {
            return PointChangeDto.fromEntity(this.pointRepository.save(PointEntity.builder()
                    .changedPoint(money)
                    .currentPoint(pointEntity.getCurrentPoint() + money)
                    .eventType("포인트 구매") //enum 타입으로 바꿀 예정
                    .userEntity(userEntity)
                    .createdDate(LocalDateTime.now())
                    .build())
            );
        }
    }

    //현재 포인트 조회
    //이메일로 memberEntity 검색
    public GetPoint getPoint(User userEntity) {
        PointEntity pointEntity = getLatestPointForEntity(userEntity);

        if (pointEntity == null) {
            throw new PointException(NO_POINT_USING_HISTORY);
        }

        return GetPoint.builder()
                .currentPoint(pointEntity.getCurrentPoint())
                .build();
    }

    //특정 기간 사이의 포인드 사용 내역 조회
    public List<PointHistory> getPointHistory(LocalDateTime startDateTime,
                                              LocalDateTime endDateTime, User userEntity) {

        List<PointEntity> pointEntityList =
                this.pointRepository.findAllByUserEntityAndCreatedDateBetween(
                        userEntity, startDateTime, endDateTime);

        if (pointEntityList.isEmpty()) {
            throw new PointException(NO_POINT_USING_HISTORY);
        }

        return pointEntityList.stream()
                .map(e -> new PointHistory(e.getId(), e.getChangedPoint(),
                        e.getCurrentPoint(), e.getEventType(), e.getCreatedDate()))
                .collect(Collectors.toList());
    }

    //포인트 환불
    //pointEntity == null인 경우(거래가 한번도 없는 경우)와
    //환불 포인드가 잔액 포인트 보다 많은 경우 예외 발생
    @Transactional
    public PointChangeDto refundPoint(User userEntity, Integer refundPoint) {
        PointEntity pointEntity = getLatestPointForEntity(userEntity);

        if (pointEntity == null || pointEntity.getCurrentPoint() < refundPoint) {
            throw new PointException(NOT_ENOUGH_POINT_BALANCE);
        } else {
            Integer currentPoint = pointEntity.getCurrentPoint() - refundPoint;

            return PointChangeDto.fromEntity(this.pointRepository.save(PointEntity.builder()
                    .userEntity(userEntity)
                    .changedPoint(refundPoint * -1)
                    .currentPoint(currentPoint)
                    .eventType("포인트 환불")
                    .createdDate(LocalDateTime.now())
                    .build())
            );
        }
    }

    private PointEntity getLatestPointForEntity(User memberEntity) {
        //pageable로 가장 최근의 포인트 내역 1개만 조회
        Pageable pageable = PageRequest.of(0, 1);

        Page<PointEntity> pointEntityPage
                = this.pointRepository.findByUserEntity(memberEntity, pageable);

        List<PointEntity> pointEntityList = pointEntityPage.getContent();

        return pointEntityList.isEmpty() ? null : pointEntityList.get(0);
    }
}
