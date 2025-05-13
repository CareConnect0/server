package smwu.heartcall.domain.aiAssistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiMessageRequestDto {
    @NotNull
    private Long roomId;

    @NotBlank
    private String responseMessage;
}
