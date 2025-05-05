package smwu.heartcall.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VerifyCodeRequestDto {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String code;
}
