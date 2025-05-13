package smwu.heartcall.domain.emergency.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.emergency.entity.Emergency;

import java.time.LocalDateTime;

@Getter
@Builder
public class EmergencyDetailResponseDto {
    private Long emergencyId;
    private String keyword;
    private String dependentName;
    private String audioLink;
    private LocalDateTime createdAt;

    public static EmergencyDetailResponseDto of(Emergency emergency) {
        return EmergencyDetailResponseDto.builder()
                .emergencyId(emergency.getId())
                .keyword(emergency.getKeyword())
                .dependentName(emergency.getDependent().getName())
                .audioLink(emergency.getAudioUrl())
                .createdAt(emergency.getCreatedAt())
                .build();
    }
}
