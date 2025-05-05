package smwu.heartcall.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuardianCreateScheduleRequestDto {
    @NotNull
    private Long dependentId;

    @NotBlank
    private String content;

    @NotBlank
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$",
            message = "날짜 형식은 YYYY-MM-DD HH:mm 이어야 합니다."
    )
    private String startTime;
}
