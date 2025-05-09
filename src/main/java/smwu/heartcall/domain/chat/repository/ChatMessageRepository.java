package smwu.heartcall.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
