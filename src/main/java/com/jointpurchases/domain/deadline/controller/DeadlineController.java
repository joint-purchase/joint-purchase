package com.jointpurchases.domain.deadline.controller;

import com.jointpurchases.domain.deadline.model.dto.CeateDeadlineDto;
import com.jointpurchases.domain.deadline.model.dto.ModifyDeadlineDto;
import com.jointpurchases.domain.deadline.service.DeadlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deadline")
public class DeadlineController {
    private final DeadlineService deadlineService;
/*
마감 기간 생성
 */
    @PostMapping
    public CeateDeadlineDto.Response createDeadline(@RequestPart(name = "dto") CeateDeadlineDto.Request request){
        return deadlineService.createDeadline(request.getProductId(), request.getPeriod(), request.getMaximumPeople());
    }
/*
마감 기간 수정
 */
    @PutMapping
    public ModifyDeadlineDto.Response modifyDeadline(@RequestPart(name = "dto") ModifyDeadlineDto.Request request){
        return deadlineService.modifyDeadline(request.getDeadlineId(), request.getPeriod(),request.getMaximumPeople());
    }
/*
마감 기간 삭제
 */
    @DeleteMapping
    public long deleteDeadline(@RequestParam(name = "id") long deadlineId){
        return deadlineService.deleteDeadline(deadlineId);
    }

}
