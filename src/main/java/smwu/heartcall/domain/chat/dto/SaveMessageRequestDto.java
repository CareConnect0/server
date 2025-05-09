package smwu.heartcall.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SaveMessageRequestDto {
    @NotBlank
    private String content;
}
