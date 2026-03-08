package smwu.heartcall.domain.aiAssistant.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantMessage;

import java.util.List;

@Getter
@Builder
public class AssistantMessageScrollResponseDto {
    private List<AssistantMessageResponseDto> responseDtoList;
    private boolean hasNext;

    public static AssistantMessageScrollResponseDto of(Page<AiAssistantMessage> messagePage, boolean hasNext) {
        return AssistantMessageScrollResponseDto.builder()
                .responseDtoList(messagePage.getContent().stream().map(AssistantMessageResponseDto::of).toList())
                .hasNext(hasNext)
                .build();
    }
}
