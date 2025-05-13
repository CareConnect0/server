package smwu.heartcall.domain.aiAssistant.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantRoom;

@Getter
@Builder
public class AssistantRoomResponseDto {
    private Long roomId;

    public static AssistantRoomResponseDto of(AiAssistantRoom assistantRoom) {
        return AssistantRoomResponseDto.builder()
                .roomId(assistantRoom.getId())
                .build();
    }
}

