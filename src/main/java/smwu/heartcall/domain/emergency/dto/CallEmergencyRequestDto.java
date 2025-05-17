package smwu.heartcall.domain.emergency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CallEmergencyRequestDto {
    @NotBlank
    private String audioUrl;

    private List<@NotBlank String> keyword;
}
