package smwu.heartcall.domain.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmergencyAudioUrlResponseDto {
    private String url;

    public static EmergencyAudioUrlResponseDto of(String url) {
        return new EmergencyAudioUrlResponseDto(url);
    }
}
