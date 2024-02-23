package com.jointpurchases.domain.cart.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartItemEntity is a Querydsl query type for CartItemEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartItemEntity extends EntityPathBase<CartItemEntity> {

    private static final long serialVersionUID = 1805363231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartItemEntity cartItemEntity = new QCartItemEntity("cartItemEntity");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QCartEntity cartEntity;

    public final NumberPath<Long> cartItemId = createNumber("cartItemId", Long.class);

    public final com.jointpurchases.domain.product.model.entity.QProduct product;

    public final NumberPath<Integer> productTotalPrice = createNumber("productTotalPrice", Integer.class);

    public QCartItemEntity(String variable) {
        this(CartItemEntity.class, forVariable(variable), INITS);
    }

    public QCartItemEntity(Path<? extends CartItemEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartItemEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartItemEntity(PathMetadata metadata, PathInits inits) {
        this(CartItemEntity.class, metadata, inits);
    }

    public QCartItemEntity(Class<? extends CartItemEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cartEntity = inits.isInitialized("cartEntity") ? new QCartEntity(forProperty("cartEntity"), inits.get("cartEntity")) : null;
        this.product = inits.isInitialized("product") ? new com.jointpurchases.domain.product.model.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

