package smwu.heartcall.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import smwu.heartcall.domain.chat.dto.SaveMessageRequestDto;
import smwu.heartcall.domain.chat.service.ChatMessageService;
import smwu.heartcall.global.security.UserDetailsImpl;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chats/rooms/{roomId}/messages")
    public void sendMessage(
            Principal principal,
            @DestinationVariable final Long roomId,
            SaveMessageRequestDto requestDto
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        chatMessageService.sendMessage(userDetails.getUser(), roomId, requestDto);
    }
}
