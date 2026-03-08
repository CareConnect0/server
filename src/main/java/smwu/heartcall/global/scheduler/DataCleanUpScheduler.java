package smwu.heartcall.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.aiAssistant.repository.AiAssistantMessageRepository;
import smwu.heartcall.domain.emergency.repository.EmergencyRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataCleanUpScheduler {
    private final EmergencyRepository emergencyRepository;
    private final AiAssistantMessageRepository assistantMessageRepository;

    @Scheduled(cron = "0 0 * * * *") // 매 정시마다 실행
    @Transactional
    public void cleanUpEmergencyData() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime emergencyCutOff = now.minusHours(24);
        int emergencyDeleted = emergencyRepository.deleteByCreatedAtBefore(emergencyCutOff);

        log.info("[데이터 정리] {} 기준 비상호출 데이터 삭제 완료 : 비상호출 {}건", now, emergencyDeleted);
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void cleanUpAiMessageData() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime messageCutOff = now.minusDays(7);
        int messageDeleted  = assistantMessageRepository.deleteByCreatedAtBefore(messageCutOff);
        log.info("[데이터 정리] {} 기준 AI 비서 메시지 데이터 삭제 완료 : 메시지 {}건", now, messageDeleted);
    }
}
