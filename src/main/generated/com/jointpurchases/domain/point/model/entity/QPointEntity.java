package com.jointpurchases.domain.point.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointEntity is a Querydsl query type for PointEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointEntity extends EntityPathBase<PointEntity> {

    private static final long serialVersionUID = -859534596L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointEntity pointEntity = new QPointEntity("pointEntity");

    public final NumberPath<Long> changedPoint = createNumber("changedPoint", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> currentPoint = createNumber("currentPoint", Long.class);

    public final StringPath eventType = createString("eventType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMemberEntity memberEntity;

    public QPointEntity(String variable) {
        this(PointEntity.class, forVariable(variable), INITS);
    }

    public QPointEntity(Path<? extends PointEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointEntity(PathMetadata metadata, PathInits inits) {
        this(PointEntity.class, metadata, inits);
    }

    public QPointEntity(Class<? extends PointEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
    }

}

