package smwu.heartcall.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.chat.dto.SaveMessageRequestDto;
import smwu.heartcall.domain.chat.entity.ChatMessage;
import smwu.heartcall.domain.chat.repository.ChatMessageRepository;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void sendMessage(Long roomId, SaveMessageRequestDto requestDto) {
        ChatMessage chatMessage = ChatMessage.builder()
                .
                .build();
    }

    messagingTemplate.convertAndSend("/queue/chats/rooms" + message.getRoomId(), message);
}
