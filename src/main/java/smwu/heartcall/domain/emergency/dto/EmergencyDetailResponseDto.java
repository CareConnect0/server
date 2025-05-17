package smwu.heartcall.domain.emergency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.emergency.entity.Emergency;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EmergencyDetailResponseDto {
    private Long emergencyId;
    private String dependentName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> keyword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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
