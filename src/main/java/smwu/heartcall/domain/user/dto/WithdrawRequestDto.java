package smwu.heartcall.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawRequestDto {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String password;
}
