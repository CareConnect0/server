package smwu.heartcall.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnReadNotificationResponseDto {
    private boolean hasUnreadSchedule;
    private boolean hasUnreadChat;
    private boolean hasUnreadEmergency;

    public static UnReadNotificationResponseDto of(
            boolean hasUnreadSchedule,
            boolean hasUnreadChat,
            boolean hasUnreadEmergency
    ) {
        return UnReadNotificationResponseDto.builder()
                .hasUnreadSchedule(hasUnreadSchedule)
                .hasUnreadChat(hasUnreadChat)
                .hasUnreadEmergency(hasUnreadEmergency)
                .build();
    }
}
