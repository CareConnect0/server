package smwu.heartcall.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditPasswordRequestDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)(?!.*\\s).{8,15}$", message = "비밀번호는 8-15자 사이, 영어, 숫자, 특수기호를 포함해야 합니다.")
    private String newPassword;
}
