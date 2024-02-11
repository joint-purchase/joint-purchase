package com.jointpurchases.domain.security.config;

import com.jointpurchases.domain.security.entity.Member;
import com.jointpurchases.domain.security.model.MemberRole;
import com.jointpurchases.domain.security.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
// 프로그램 시작부터 관리자 권한 생성
public class AdminConfig {
    private static final Logger log = LoggerFactory.getLogger(AdminConfig.class);

    @Bean
    CommandLineRunner initDatabase(MemberRepository memberRepository) {

        return args -> log.info("Preloading " + memberRepository.save(
                Member.builder()
                        .name("admin")
                        .password("admin")
                        .phone("01012345678")
                        .email("admin@admin.com")
                        .birth(LocalDate.parse("2077-02-07"))
                        .role(MemberRole.ADMIN)
                        .build()
                ));
    }
}
