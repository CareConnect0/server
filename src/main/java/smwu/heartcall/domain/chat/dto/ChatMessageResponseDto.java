package smwu.heartcall.domain.chat.dto;

import kotlin.BuilderInference;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponseDto {
    private Long messageId;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime sentAt;

    public static ChatMessageResponseDto of(ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .messageId(chatMessage.getId())
                .senderId(chatMessage.getSender().getId())
                .senderName(chatMessage.getSender().getName())
                .content(chatMessage.getContent())
                .sentAt(chatMessage.getCreatedAt())
                .build();
    }
}
