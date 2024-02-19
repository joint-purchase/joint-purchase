package com.jointpurchases.domain.order.service;

import com.jointpurchases.domain.cart.model.entity.CartEntity;
import com.jointpurchases.domain.cart.model.entity.CartItemEntity;
import com.jointpurchases.domain.cart.model.entity.MemberEntity;
import com.jointpurchases.domain.cart.model.entity.ProductEntity;
import com.jointpurchases.domain.cart.repository.CartItemRepository;
import com.jointpurchases.domain.cart.repository.CartRepository;
import com.jointpurchases.domain.cart.repository.MemberRepository;
import com.jointpurchases.domain.cart.repository.ProductRepository;
import com.jointpurchases.domain.order.model.dto.CancelOrder;
import com.jointpurchases.domain.order.model.dto.OrderDto;
import com.jointpurchases.domain.order.model.entity.OrderEntity;
import com.jointpurchases.domain.order.repository.OrderRepository;
import com.jointpurchases.domain.point.model.entity.PointEntity;
import com.jointpurchases.domain.point.repository.PointRepository;
import com.jointpurchases.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final PointRepository pointRepository;
    private final ProductRepository productRepository;
    private final PointService pointService;

    //상품 주문
    @Transactional
    public OrderDto createOrder(String email, Long money, String address) {
        MemberEntity memberEntity = getMemberEntity(email);

        CartEntity cartEntity = getCartEntity(memberEntity);

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        if (cartItemEntityList.isEmpty()) {
            throw new RuntimeException("장바구니에 담긴 상품이 없습니다.");
        }

        Long totalPrice = cartEntity.getTotalPrice();
        if (totalPrice > money) {
            throw new RuntimeException("결제 금액이 부족합니다.");
        }

        Long currentPoint = this.pointService.getPoint(email).getCurrentPoint();
        if (currentPoint < money) {
            throw new RuntimeException("포인트 잔액이 부족합니다.");
        }

        OrderEntity orderEntity = this.orderRepository.save(OrderEntity.builder()
                .cartEntity(cartEntity)
                .memberEntity(memberEntity)
                .orderedDate(LocalDateTime.now())
                .payment("결제 완료")
                .address(address)
                .type("일반 구매")
                .build());

        List<Long> productIdList = cartItemEntityList.stream().map(item ->
                item.getProductEntity().getProductId()).toList();

        decreaseProductStock(productIdList, cartItemEntityList);

        this.pointRepository.save(PointEntity.builder()
                .memberEntity(memberEntity)
                .changedPoint(money * -1)
                .currentPoint(currentPoint - money)
                .eventType("상품 구매")
                .createdDate(LocalDateTime.now())
                .build());

        return OrderDto.from(productIdList, address, orderEntity);
    }

    //상품 주문 취소
    @Transactional
    public CancelOrder.Response cancelOrder(String email, Long orderId) {
        MemberEntity memberEntity = getMemberEntity(email);

        OrderEntity orderEntity = this.orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException(" 잘못된 주문 번호 입니다."));

        if (orderEntity.getMemberEntity() != memberEntity) {
            throw new RuntimeException("구매자의 정보가 일치하지 않습니다.");
        }

        //주문 취소로 주문 상태 변경
        orderEntity.cancelOrder();

        //장바구니의 상품들 재고 증가
        CartEntity cartEntity = this.cartRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new RuntimeException("장바구니가 없습니다."));

        List<CartItemEntity> cartItemEntityList =
                this.cartItemRepository.findAllByCartEntity(cartEntity);

        List<Long> productIdList = cartItemEntityList.stream().map(item ->
                item.getProductEntity().getProductId()).toList();

        increaseProductStock(productIdList, cartItemEntityList);

        //포인트 환불
        Long refundPoint = cartEntity.getTotalPrice();

        Long currentPoint = this.pointService.getPoint(email).getCurrentPoint();

        this.pointRepository.save(PointEntity.builder()
                .memberEntity(memberEntity)
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
        Map<Long, ProductEntity> productEntityMap = getProductEntityMap(productIdList);

        cartItemEntityList.forEach(cartItem -> {
            Long productId = cartItem.getProductEntity().getProductId();
            ProductEntity productEntity = productEntityMap.get(productId);
            if (productEntity == null) {
                throw new RuntimeException("상품 정보를 찾을 수 없습니다.");
            }
            Long amount = cartItem.getAmount();
            productEntity.decreaseStock(amount);
        });

        this.productRepository.saveAll(productEntityMap.values());
    }

    //주문 취소된 상품의 재고 증가
    private void increaseProductStock(List<Long> productIdList, List<CartItemEntity> cartItemEntityList) {
        Map<Long, ProductEntity> productEntityMap = getProductEntityMap(productIdList);

        cartItemEntityList.forEach(cartItem -> {
            Long productId = cartItem.getProductEntity().getProductId();
            ProductEntity productEntity = productEntityMap.get(productId);
            if (productEntity == null) {
                throw new RuntimeException("상품 정보를 찾을 수 없습니다.");
            }
            Long amount = cartItem.getAmount();
            productEntity.increaseStock(amount);
        });

        this.productRepository.saveAll(productEntityMap.values());
    }

    //주문 상품의 productIdList를 입력받아 ProductEntity를 일괄 검색 >> map으로 만들어 반환
    private Map<Long, ProductEntity> getProductEntityMap(List<Long> productIdList) {
        List<ProductEntity> productEntityList
                = this.productRepository.findAllByProductIdIn(productIdList);

        return productEntityList.stream().collect(Collectors
                .toMap(ProductEntity::getProductId, Function.identity()));
    }

    private CartEntity getCartEntity(MemberEntity memberEntity) {
        return cartRepository.findByMemberEntity(memberEntity)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
    }

    private MemberEntity getMemberEntity(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다."));
    }

}
