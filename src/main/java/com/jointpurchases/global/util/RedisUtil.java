package com.jointpurchases.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void deleteLike(String key) {
        redisTemplate.delete(key);
    }

    public void like(Long userId, Long productId){
        redisTemplate.opsForValue()
                .increment(productLikesKey(productId));
        redisTemplate.opsForSet()
                .add(userLikedProductKey(userId, productId), String.valueOf(productId));
    }

    public void unLike(Long userId, Long productId){
        redisTemplate.opsForValue()
                .decrement(productLikesKey(productId));
        redisTemplate.opsForSet().
                remove(userLikedProductKey(userId, productId), String.valueOf(productId));
    }

    public int getLikeCount(Long productId){
        String key = String.valueOf(redisTemplate.opsForValue().get(productLikesKey(productId)));
        if(Objects.equals(key, "null"))  return 0;

        return Integer.parseInt(key);
    }

    public Boolean isLike(Long userId, Long productId) {
        return redisTemplate.opsForSet().isMember(userLikedProductKey(userId, productId), String.valueOf(productId));
    }


    private String productLikesKey(Long productId) {
        return "product:" + productId + ":like";
    }

    private String userLikedProductKey(Long userId, Long productId) {
        return "user:" + userId + ":product:" + productId;
    }
}