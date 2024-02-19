package com.jointpurchases.domain.security.token;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

// 다른 API에서 토큰을 직접 다루기 위한 서비스

@Service
public class TokenManagementService {

    // 토큰을 위한 메모리 저장소 - 해시맵으로 구현
    private final Map<String, String> accessTokenStore = new HashMap<>();
    private final Map<String, String> refreshTokenStore = new HashMap<>();

    // 토큰 저장
    public void storeAccessToken(String userId, String accessToken) {
        accessTokenStore.put(userId, accessToken);
    }

    public void storeRefreshToken(String userId, String refreshToken) {
        refreshTokenStore.put(userId, refreshToken);
    }

    // 토큰 접근
    public String getAccessToken(String userId) {
        return accessTokenStore.get(userId);
    }

    public String getRefreshToken(String userId) {
        return refreshTokenStore.get(userId);
    }

    // 토큰 삭제
    public void removeAccessToken(String userId) {
        accessTokenStore.remove(userId);
    }

    public void removeRefreshToken(String userId) {
        refreshTokenStore.remove(userId);
    }
}
