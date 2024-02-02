package com.jointpurchases.domain.point.service;

import com.jointpurchases.domain.point.model.dto.GetPoint;
import com.jointpurchases.domain.point.model.dto.PointChangeDto;
import com.jointpurchases.domain.point.model.entity.MemberEntity;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import com.jointpurchases.domain.point.repository.MemberRepository;
import com.jointpurchases.domain.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PointService pointService;


    @Test
    @DisplayName("포인트 구매 성공")
    void buyPointSuccess() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        PointEntity point = PointEntity.builder()
                .id(3L)
                .eventType("포인트 구매")
                .changedPoint(4000L)
                .currentPoint(4000L)
                .memberEntity(member)
                .build();

        //인수가 하나인 리스트 만들기
        List<PointEntity> pointEntityList = Collections.singletonList(point);

        Pageable pageable = PageRequest.of(0, 1);
        Page<PointEntity> page = new PageImpl<>(pointEntityList, pageable, pointEntityList.size());

        given(memberRepository.findByEmail(anyString()))
                .willReturn(Optional.ofNullable(member));
        given(pointRepository.findByMemberEntity(member, pageable))
                .willReturn(page);

        //save는 optional을 반환하지 않고 지정된 entity를 반환한다
        //여기서 willReturn(Optional.of(point)); 로 작성해서 계속 오류났음
        given(pointRepository.save(any()))
                .willReturn(PointEntity.builder()
                        .changedPoint(1000L)
                        .currentPoint(5000L)
                        .eventType("포인트 구매")
                        .createdDate(LocalDateTime.now())
                        .memberEntity(member)
                        .build());

        //when
        PointChangeDto pointChangeDto = pointService.buyPoint("db@naver.com", 1000L);
        ArgumentCaptor<PointEntity> captor = ArgumentCaptor.forClass(PointEntity.class);

        //then
        verify(pointRepository, times(1)).save(captor.capture());
        assertAll(
                () -> assertEquals(1000L, captor.getValue().getChangedPoint()),
                () -> assertEquals(5000L, captor.getValue().getCurrentPoint()),
                () -> assertEquals(1000L, pointChangeDto.getChangedPoint()),
                () -> assertEquals(5000L, pointChangeDto.getCurrentPoint()),
                () -> assertEquals("dbdbdb@naver.com", pointChangeDto.getEmail()),
                () -> assertEquals("포인트 구매", pointChangeDto.getEventType())
        );
    }

    @Test
    @DisplayName("현재 포인트 조회")
    void getPointSuccess() {
        //given
        MemberEntity member = MemberEntity.builder()
                .id(11L)
                .email("dbdbdb@naver.com")
                .build();

        PointEntity point = PointEntity.builder()
                .id(3L)
                .eventType("포인트 구매")
                .changedPoint(4000L)
                .currentPoint(4000L)
                .memberEntity(member)
                .build();

        List<PointEntity> pointEntityList = Collections.singletonList(point);

        Pageable pageable = PageRequest.of(0, 1);
        Page<PointEntity> page = new PageImpl<>(pointEntityList, pageable, pointEntityList.size());

        given(memberRepository.findByEmail(anyString()))
                .willReturn(Optional.ofNullable(member));
        given(pointRepository.findByMemberEntity(member, pageable))
                .willReturn(page);

        //when
        GetPoint getPoint = pointService.getPoint(anyString());

        //then
        assertEquals(4000L, getPoint.getCurrentPoint());
    }

}