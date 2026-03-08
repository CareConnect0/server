package smwu.heartcall.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.chat.entity.ChatRoom;

@Getter
@Builder
public class ChatRoomResponseDto {
    private Long roomId;

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getId())
                .build();
    }
}
