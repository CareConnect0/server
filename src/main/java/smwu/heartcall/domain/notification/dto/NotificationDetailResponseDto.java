package smwu.heartcall.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.notification.enums.NotificationType;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDetailResponseDto {
    private Long notificationId;
    private NotificationType notificationType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long dependentId;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String relatedUrl;

    public static NotificationDetailResponseDto of(Notification notification) {
        return NotificationDetailResponseDto.builder()
                .notificationId(notification.getId())
                .notificationType(notification.getNotificationType())
                .dependentId(notification.getSender().getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .relatedUrl(notification.getRelatedUrl())
                .build();
    }
}
