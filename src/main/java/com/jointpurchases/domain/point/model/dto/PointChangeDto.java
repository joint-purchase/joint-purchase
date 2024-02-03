package com.jointpurchases.domain.point.model.dto;

import com.jointpurchases.domain.point.model.entity.PointEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointChangeDto {
    private String email;
    private Long changedPoint;
    private Long currentPoint;
    private String eventType;
    private LocalDateTime createdDate;

    public static PointChangeDto fromEntity(PointEntity pointEntity) {
        return PointChangeDto.builder()
                .email(pointEntity.getMemberEntity().getEmail())
                .changedPoint(pointEntity.getChangedPoint())
                .currentPoint(pointEntity.getCurrentPoint())
                .eventType(pointEntity.getEventType())
                .createdDate(pointEntity.getCreatedDate())
                .build();
    }
}
