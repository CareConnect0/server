package smwu.heartcall.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendCodeRequestDto {
    @NotBlank
    private String phoneNumber;
}
