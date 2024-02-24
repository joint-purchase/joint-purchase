package com.jointpurchases.domain.order.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.cart.repository.CartItemRepository;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.order.model.dto.CancelOrder;
import com.jointpurchases.domain.order.model.dto.OrderDto;
import com.jointpurchases.domain.order.model.entity.OrderEntity;
import com.jointpurchases.domain.order.model.entity.OrderedItemEntity;
import com.jointpurchases.domain.order.repository.OrderRepository;
import com.jointpurchases.domain.order.repository.OrderedItemRepository;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import com.jointpurchases.domain.point.repository.PointRepository;
import com.jointpurchases.domain.point.service.PointService;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PointRepository pointRepository;
    private final ProductRepository productRepository;
    private final PointService pointService;
    private final OrderedItemRepository orderedItemRepository;

    //상품 주문
    @Transactional
    public OrderDto createOrder(User userEntity, Integer money, String address) {
        CartEntity cartEntity = getCartEntity(userEntity);

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        if (cartItemEntityList.isEmpty()) {
            throw new RuntimeException("장바구니에 담긴 상품이 없습니다.");
        }

        Integer totalPrice = cartEntity.getTotalPrice();
        if (totalPrice > money) {
            throw new RuntimeException("결제 금액이 부족합니다.");
        }

        Integer currentPoint = this.pointService.getPoint(userEntity).getCurrentPoint();
        if (currentPoint < money) {
            throw new RuntimeException("포인트 잔액이 부족합니다.");
        }

        OrderEntity orderEntity = this.orderRepository.save(OrderEntity.builder()
                .cartEntity(cartEntity)
                .userEntity(userEntity)
                .orderedDate(LocalDateTime.now())
                .payment("결제 완료")
                .address(address)
                .type("일반 구매")
                .build());

        //cartItem의 상품들 orderedItem테이블에 저장
        List<Long> productIdList = cartItemEntityList.stream().map(item ->
                item.getProduct().getId()).toList();

        List<OrderedItemEntity> orderedItemEntities = cartItemEntityList.stream().map(cartItem -> OrderedItemEntity.builder()
                .orderEntity(orderEntity)
                .productEntity(cartItem.getProduct())
                .amount(cartItem.getAmount())
                .productTotalPrice(cartItem.getProductTotalPrice())
                .build()).toList();

        this.orderedItemRepository.saveAll(orderedItemEntities);

        //장바구니의 상품들 삭제
        this.cartItemRepository.deleteAll(cartItemEntityList);

        //상품의 재고 차감
        decreaseProductStock(productIdList, cartItemEntityList);

        this.pointRepository.save(PointEntity.builder()
                .userEntity(userEntity)
                .changedPoint(money * -1)
                .currentPoint(currentPoint - money)
                .eventType("상품 구매")
                .createdDate(LocalDateTime.now())
                .build());

        return OrderDto.from(productIdList, address, orderEntity);
    }

    //상품 주문 취소
    @Transactional
    public CancelOrder.Response cancelOrder(User userEntity, Long orderId) {
        OrderEntity orderEntity = this.orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException(" 잘못된 주문 번호 입니다."));

        if (!Objects.equals(orderEntity.getUserEntity().getEmail(), userEntity.getEmail())) {
            throw new RuntimeException("구매자의 정보가 일치하지 않습니다.");
        }

        //주문 취소로 주문 상태 변경
        orderEntity.cancelOrder();

        //장바구니의 상품들 재고 증가
        CartEntity cartEntity = this.cartRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new RuntimeException("장바구니가 없습니다."));

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        List<Long> productIdList = cartItemEntityList.stream().map(item ->
                item.getProduct().getId()).toList();

        increaseProductStock(productIdList, cartItemEntityList);

        //포인트 환불
        Integer refundPoint = cartEntity.getTotalPrice();

        Integer currentPoint = this.pointService.getPoint(userEntity).getCurrentPoint();

        this.pointRepository.save(PointEntity.builder()
                .userEntity(userEntity)
                .changedPoint(refundPoint)
                .currentPoint(currentPoint + refundPoint)
                .createdDate(LocalDateTime.now())
                .eventType("상품 주문 취소")
                .build());

        return CancelOrder.Response.from(this.orderRepository.save(orderEntity),
                refundPoint);
    }

    //주문된 상품의 재고 차감
    private void decreaseProductStock(List<Long> productIdList, List<CartItemEntity> cartItemEntityList) {
        Map<Long, Product> productEntityMap = getProductEntityMap(productIdList);

        cartItemEntityList.forEach(cartItem -> {
            Long productId = cartItem.getProduct().getId();
            Product product = productEntityMap.get(productId);
            if (product == null) {
                throw new RuntimeException("상품 정보를 찾을 수 없습니다.");
            }
            Integer amount = cartItem.getAmount();
            product.decreaseStock(amount);
        });

        this.productRepository.saveAll(productEntityMap.values());
    }

    //주문 취소된 상품의 재고 증가
    private void increaseProductStock(List<Long> productIdList, List<CartItemEntity> cartItemEntityList) {
        Map<Long, Product> productEntityMap = getProductEntityMap(productIdList);

        cartItemEntityList.forEach(cartItem -> {
            Long productId = cartItem.getProduct().getId();
            Product product = productEntityMap.get(productId);
            if (product == null) {
                throw new RuntimeException("상품 정보를 찾을 수 없습니다.");
            }
            Integer amount = cartItem.getAmount();
            product.increaseStock(amount);
        });

        this.productRepository.saveAll(productEntityMap.values());
    }

    //주문 상품의 productIdList를 입력받아 ProductEntity를 일괄 검색 >> map으로 만들어 반환
    private Map<Long, Product> getProductEntityMap(List<Long> productIdList) {
        List<Product> productList
                = this.productRepository.findAllByIdIn(productIdList);

        return productList.stream().collect(Collectors
                .toMap(Product::getId, Function.identity()));
    }

    private CartEntity getCartEntity(User userEntity) {
        return cartRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
    }
}
