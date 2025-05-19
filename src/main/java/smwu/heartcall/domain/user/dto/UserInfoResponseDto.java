package smwu.heartcall.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.enums.UserType;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profileUrl;
    private UserType userType;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .profileUrl(user.getProfileUrl())
                .userType(user.getUserType())
                .build();
    }
}
