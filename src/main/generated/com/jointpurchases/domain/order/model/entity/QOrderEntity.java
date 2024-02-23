package com.jointpurchases.domain.order.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderEntity is a Querydsl query type for OrderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderEntity extends EntityPathBase<OrderEntity> {

    private static final long serialVersionUID = -565144004L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderEntity orderEntity = new QOrderEntity("orderEntity");

    public final StringPath address = createString("address");

    public final com.jointpurchases.domain.cart.model.entity.QCartEntity cartEntity;

    public final com.jointpurchases.domain.cart.model.entity.QMemberEntity memberEntity;

    public final DateTimePath<java.time.LocalDateTime> orderedDate = createDateTime("orderedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath payment = createString("payment");

    public final StringPath type = createString("type");

    public QOrderEntity(String variable) {
        this(OrderEntity.class, forVariable(variable), INITS);
    }

    public QOrderEntity(Path<? extends OrderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderEntity(PathMetadata metadata, PathInits inits) {
        this(OrderEntity.class, metadata, inits);
    }

    public QOrderEntity(Class<? extends OrderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cartEntity = inits.isInitialized("cartEntity") ? new com.jointpurchases.domain.cart.model.entity.QCartEntity(forProperty("cartEntity"), inits.get("cartEntity")) : null;
        this.memberEntity = inits.isInitialized("memberEntity") ? new com.jointpurchases.domain.cart.model.entity.QMemberEntity(forProperty("memberEntity")) : null;
    }

}

