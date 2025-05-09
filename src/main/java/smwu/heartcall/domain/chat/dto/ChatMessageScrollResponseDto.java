package smwu.heartcall.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import smwu.heartcall.domain.chat.entity.ChatMessage;

import java.util.List;

@Getter
@Builder
public class ChatMessageScrollResponseDto {
    private List<ChatMessageResponseDto> responseDtoList;
    private boolean hasNext;

    public static ChatMessageScrollResponseDto of(Page<ChatMessage> messagePage, boolean hasNext) {
        return ChatMessageScrollResponseDto.builder()
                .responseDtoList(messagePage.getContent().stream().map(ChatMessageResponseDto::of).toList())
                .hasNext(hasNext)
                .build();
    }
}
