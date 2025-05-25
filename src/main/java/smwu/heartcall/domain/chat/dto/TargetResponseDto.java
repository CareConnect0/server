package smwu.heartcall.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.user.entity.User;

@Getter
@Builder
public class TargetResponseDto {
    private Long userId;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profileUrl;

    public static TargetResponseDto of(User user) {
        return TargetResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .profileUrl(user.getProfileUrl())
                .build();

    }
}
