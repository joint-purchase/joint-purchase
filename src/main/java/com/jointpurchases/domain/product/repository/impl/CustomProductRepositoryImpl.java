package com.jointpurchases.domain.product.repository.impl;

import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.CustomProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jointpurchases.domain.product.model.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Product> findProductWithProductId(Long productId) {

        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(product)
                .leftJoin(product.user).fetchJoin()
                .leftJoin(product.category).fetchJoin()
                .leftJoin(product.productImages).fetchJoin()
                .where(product.id.eq(productId))
                .fetchOne());
    }

    @Override
    public Page<Product> findAllProduct(Pageable pageable) {
        List<Product> products = jpaQueryFactory
                .selectFrom(product)
                .leftJoin(product.category).fetchJoin()
                .leftJoin(product.productImages).fetchJoin()
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(jpaQueryFactory
                        .select(product.count())
                        .from(product)
                        .fetchOne())
                        .orElse(0L);

        return new PageImpl<>(products,pageable,total);
    }
}