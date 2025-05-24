package smwu.heartcall.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnReadNotificationResponseDto {
    private boolean hasUnread;

    public static UnReadNotificationResponseDto of(boolean hasUnread) {
        return UnReadNotificationResponseDto.builder()
                .hasUnread(hasUnread)
                .build();
    }
}
