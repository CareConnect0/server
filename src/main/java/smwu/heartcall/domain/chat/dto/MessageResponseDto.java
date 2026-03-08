package smwu.heartcall.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageResponseDto {
    private Long messageId;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime createdAt;

    public static MessageResponseDto of(ChatMessage chatMessage) {
        return MessageResponseDto.builder()
                .messageId(chatMessage.getId())
                .senderId(chatMessage.getSender().getId())
                .senderName(chatMessage.getSender().getName())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
