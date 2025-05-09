package smwu.heartcall.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.heartcall.domain.chat.dto.SaveMessageRequestDto;
import smwu.heartcall.domain.chat.entity.ChatMessage;
import smwu.heartcall.domain.chat.service.ChatMessageService;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/chats/")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/rooms/{roomId}/messages") // /app/chat/send
    public void sendMessage(
            @AuthenticationPrincipal
            @DestinationVariable Long roomId,
            SaveMessageRequestDto requestDto
    ) {
        chatMessageService.sendMessage(roomId, requestDto);
        // 클라이언트는 /queue/chat/{roomId} 를 구독하고 메시지를 받는다.
    }


}
