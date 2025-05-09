package smwu.heartcall.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.user.entity.User;

@Getter
@Builder
public class TargetResponseDto {
    private Long userId;
    private String name;

    public static TargetResponseDto of(User user) {
        return TargetResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

    }
}
