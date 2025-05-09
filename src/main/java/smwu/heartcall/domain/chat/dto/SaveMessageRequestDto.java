package smwu.heartcall.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SaveMessageRequestDto {
    @NotNull
    private Long senderId;

    @NotBlank
    private String content;
}
