package com.jointpurchases.domain.point.model.Dto;

import com.jointpurchases.domain.point.model.entity.PointEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PointChangeDto {
    private String email;
    private Long changedPoint;
    private Long currentPoint;
    private String eventType;

    private LocalDateTime createdDate;

    public static PointChangeDto fromEntity(PointEntity pointEntity){
        return PointChangeDto.builder()
                .email(pointEntity.getMemberEntity().getEmail())
                .changedPoint(pointEntity.getChangedPoint())
                .currentPoint(pointEntity.getCurrentPoint())
                .eventType(pointEntity.getEventType())
                .createdDate(pointEntity.getCreatedDate())
                .build();
    }
}
