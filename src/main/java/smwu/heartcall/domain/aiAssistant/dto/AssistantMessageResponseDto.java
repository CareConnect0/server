package smwu.heartcall.domain.aiAssistant.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantMessage;
import smwu.heartcall.domain.aiAssistant.enums.SenderType;

import java.time.LocalDateTime;

@Getter
@Builder
public class AssistantMessageResponseDto {
    private Long messageId;
    private SenderType senderType;
    private String content;
    private LocalDateTime sentAt;

    public static AssistantMessageResponseDto of(AiAssistantMessage message) {
        return AssistantMessageResponseDto.builder()
                .messageId(message.getId())
                .senderType(message.getSenderType())
                .content(message.getContent())
                .sentAt(message.getCreatedAt())
                .build();
    }
}
