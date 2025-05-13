package smwu.heartcall.domain.emergency.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.emergency.entity.Emergency;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetEmergencyResponseDto {
    private Long emergencyId;
    private String keyword;
    private LocalDateTime createdAt;
    private boolean isChecked;

    public static GetEmergencyResponseDto of(Emergency emergency) {
        return GetEmergencyResponseDto.builder()
                .emergencyId(emergency.getId())
                .keyword(emergency.getKeyword())
                .createdAt(emergency.getCreatedAt())
                .isChecked(emergency.isChecked())
                .build();
    }
}
