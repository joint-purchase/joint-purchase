package com.jointpurchases.domain.review.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewImageEntity is a Querydsl query type for ReviewImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewImageEntity extends EntityPathBase<ReviewImageEntity> {

    private static final long serialVersionUID = -1688160507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewImageEntity reviewImageEntity = new QReviewImageEntity("reviewImageEntity");

    public final StringPath filename = createString("filename");

    public final StringPath filepath = createString("filepath");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QReviewEntity review;

    public final DateTimePath<java.time.LocalDateTime> uploadDate = createDateTime("uploadDate", java.time.LocalDateTime.class);

    public QReviewImageEntity(String variable) {
        this(ReviewImageEntity.class, forVariable(variable), INITS);
    }

    public QReviewImageEntity(Path<? extends ReviewImageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewImageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewImageEntity(PathMetadata metadata, PathInits inits) {
        this(ReviewImageEntity.class, metadata, inits);
    }

    public QReviewImageEntity(Class<? extends ReviewImageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReviewEntity(forProperty("review")) : null;
    }

}

