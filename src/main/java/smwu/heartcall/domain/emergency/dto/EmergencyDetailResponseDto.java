package smwu.heartcall.domain.emergency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.emergency.entity.Emergency;
import smwu.heartcall.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EmergencyDetailResponseDto {
    private Long emergencyId;
    private String dependentName;
    private String dependentPhoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> keyword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String audioLink;
    private LocalDateTime createdAt;

    public static EmergencyDetailResponseDto of(Emergency emergency) {
        User dependent = emergency.getDependent();

        return EmergencyDetailResponseDto.builder()
                .emergencyId(emergency.getId())
                .dependentName(dependent.getName())
                .dependentPhoneNumber(dependent.getPhoneNumber())
                .keyword(emergency.getKeyword())
                .audioLink(emergency.getAudioUrl())
                .createdAt(emergency.getCreatedAt())
                .build();
    }
}
