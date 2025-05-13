package smwu.heartcall.domain.emergencyCall.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CallEmergencyRequestDto {
    @NotBlank
    private String audioUrl;

    @NotBlank
    private String keyword;
}
