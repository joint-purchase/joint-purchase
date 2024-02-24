package com.jointpurchases.domain.order.model.entity;

import com.jointpurchases.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ordered_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderedItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productEntity;

    private Integer amount;

    private Integer productTotalPrice;

}
