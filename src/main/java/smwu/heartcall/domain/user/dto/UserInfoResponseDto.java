package smwu.heartcall.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.enums.UserType;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String name;
    private UserType userType;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .userType(user.getUserType())
                .build();
    }
}
