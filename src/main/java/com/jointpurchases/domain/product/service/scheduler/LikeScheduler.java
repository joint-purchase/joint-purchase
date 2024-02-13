package com.jointpurchases.domain.product.service.scheduler;

import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeScheduler {

    private final ProductRepository productRepository;
    private final RedisUtil redisUtil;


    @Scheduled(cron = "0 0 */2 * * *") // 매 2시간마다 실행
    @Transactional
    public void likeCountDBUpdate(){
        Set<String> likeKeys = redisUtil.getKeys("product:*:like");
        if (likeKeys.isEmpty()) return;

        for (String key : likeKeys) {
            Long productId = Long.parseLong(key.split(":")[1]);
            String likeCount = String.valueOf(redisUtil.getLikeCount(productId));

            productRepository.findById(productId).ifPresent(like ->
                    like.likeCountUpdate( like.getLikeCount()+Integer.parseInt((likeCount))));

            redisUtil.deleteLike(key);
        }
    }
}