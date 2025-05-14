package smwu.heartcall.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smwu.heartcall.domain.user.enums.UserType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)(?!.*\\s).{8,15}$\n")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private UserType userType;
}
