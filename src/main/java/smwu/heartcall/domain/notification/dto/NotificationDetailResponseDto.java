package smwu.heartcall.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.notification.entity.Notification;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDetailResponseDto {
    private Long notificationId;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationDetailResponseDto of(Notification notification) {
        return NotificationDetailResponseDto.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
