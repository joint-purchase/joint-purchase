package com.jointpurchases.domain.security.contoller;

import com.jointpurchases.domain.security.dto.JoinDto;
import com.jointpurchases.domain.security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(JoinDto joinDto) {
        joinService.joinProcess(joinDto);

        return ResponseEntity.ok().build();
    }
}
