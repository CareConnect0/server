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
import smwu.heartcall.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chats/rooms/{roomId}/messages")
    public void sendMessage(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @DestinationVariable final Long roomId,
            SaveMessageRequestDto requestDto
    ) {
        chatMessageService.sendMessage(roomId, requestDto);
    }
}
