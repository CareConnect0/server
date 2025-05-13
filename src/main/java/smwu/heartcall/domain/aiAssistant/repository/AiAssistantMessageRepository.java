package smwu.heartcall.domain.aiAssistant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantMessage;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantRoom;

public interface AiAssistantMessageRepository extends JpaRepository<AiAssistantMessage, Long> {
    Page<AiAssistantMessage> findByAiAssistantRoomOrderByIdDesc(AiAssistantRoom assistantRoom, Pageable pageable);

    Page<AiAssistantMessage> findByAiAssistantRoomAndIdLessThanOrderByIdDesc(AiAssistantRoom assistantRoom, Long lastMessageId, Pageable pageable);
}
