package com.jointpurchases.domain.deadline.service;

import com.jointpurchases.domain.auth.model.entity.User;
import com.jointpurchases.domain.deadline.model.dto.ModifyDeadlineDto;
import com.jointpurchases.domain.deadline.model.entity.TeamEntity;
import com.jointpurchases.domain.order.service.OrderService;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.domain.deadline.model.dto.CeateDeadlineDto;
import com.jointpurchases.domain.deadline.model.entity.DeadlineEntity;
import com.jointpurchases.domain.deadline.repository.TeamRepository;
import com.jointpurchases.domain.deadline.repository.DeadlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeadlineService {
    private final DeadlineRepository deadlineRepository;
    private final TeamRepository teamRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    @Autowired
    private final JavaMailSender mailSender;
    private static final HashMap<Long, Timer> sendEmailMap = new HashMap<>();

/*
마감 기한 설정
*/
    public CeateDeadlineDto.Response createDeadline(long productId, int period , int maximumPeople){
        LocalDateTime now = LocalDateTime.now();
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("상품이 없습니다."));

        DeadlineEntity deadline = DeadlineEntity.builder().
                maximumPeople(maximumPeople).
                startDate(now).
                endDate(now.plusDays(period)).
                status("on").
                product(product).
                build();

        deadlineRepository.save(deadline);

        return CeateDeadlineDto.Response.builder().
                productId(productId).
                maximumPeople(deadline.getMaximumPeople()).
                startDate(deadline.getStartDate()).
                endDate(deadline.getEndDate()).
                status(deadline.getStatus()).
                build();
    }
/*
마감 기간 수정
 */
    public ModifyDeadlineDto.Response modifyDeadline(long deadlineId, int period , int maximumPeople){
        DeadlineEntity nowDeadline = deadlineRepository.findById(deadlineId).orElseThrow(() -> new RuntimeException("마감기간이 없습니다."));
        LocalDateTime newEndDate = nowDeadline.getStartDate().plusDays(period);

        if(newEndDate.isBefore(LocalDateTime.now())){
           throw new RuntimeException("날짜 설정이 잘못 되었습니다. 현재 보다 이전 날짜입니다.");
        }

        nowDeadline.updateDeadline(newEndDate, maximumPeople);

        deadlineRepository.save(nowDeadline);

        return ModifyDeadlineDto.Response.builder().
                deadlineId(deadlineId).
                maximumPeople(nowDeadline.getMaximumPeople()).
                startDate(nowDeadline.getStartDate()).
                endDate(nowDeadline.getEndDate()).
                status(nowDeadline.getStatus()).
                build();
    }
/*
마감 기간 삭제
 */
    public long deleteDeadline(long deadlineID){
        deadlineRepository.deleteById(deadlineID);
        return deadlineID;
    }
/*
주문
 */
    public void callOrder(DeadlineEntity deadline, TeamEntity team){
        List<User> user = team.getUser();

        for(User users : user){
            orderService.createOrder(users,deadline.getProduct().getPrice(),users.getAddress());
        }

        log.info(user.size()+"의 "+deadline.getProduct().getProductName()+"이 주문되었습니다.");
    }
/*
하루 마다 메일 보낼 팀 확인
 */
    @Async
    @Scheduled(cron = "* * 0 * * *")
    public void setMailing(){
        List<DeadlineEntity> teamEntities = deadlineRepository.findAll();
        for(DeadlineEntity teamEntity : teamEntities){
            if(teamEntity.getEndDate().isAfter(LocalDateTime.now()) && teamEntity.getEndDate().isBefore(LocalDateTime.now().plusDays(1)))
                sendEmailMap.put(teamEntity.getId() , setTeamTimer(teamEntity.getId(), teamEntity.getEndDate()));
        }
        log.info(sendEmailMap.size()+"개의 팀의 메일이 예약되었습니다.");
    }
/*
기간 만료 전 모든 인원 예약시 메일 보내기 예약 취소
 */
    public void cancelMail(long teamId){
        try{
            sendEmailMap.get(teamId).cancel();
        } catch (NullPointerException e){
            log.info("작업이 없습니다. "+e.getMessage());
        }
    }

/*
메일 보내기 스케줄링 설정
 */
    private Timer setTeamTimer(long teamId, LocalDateTime endDate){
        Timer timer = new Timer();
        Date parseEndDate = Timestamp.valueOf(endDate);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                expiredEndDate(teamRepository.findByDeadlineId(teamId).orElseThrow(() -> new RuntimeException("팀이 존재하지 않습니다.")));
            }
        };

        timer.schedule(timerTask, parseEndDate);

        return timer;
    }
/*
공동구매 마감기한 만료 시 메일 전송
 */
    private void expiredEndDate(TeamEntity email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<User> emailList= email.getUser();
        mailMessage.setSubject("구매 주문 동의 여부");
        mailMessage.setFrom("www.jointPurchase.com");
        mailMessage.setText("구매 하실 건가요? test");

        for(User emailId : emailList){
            mailMessage.setTo(emailId.getEmail());
            mailSender.send(mailMessage);
            log.info(emailId.getEmail()+"에게 메일보내기 완료!");
        }
    }

}
