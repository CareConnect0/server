package smwu.heartcall.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateScheduleRequestDto {
    @NotBlank
    private String content;

    @NotBlank
    private String startTime;
}
