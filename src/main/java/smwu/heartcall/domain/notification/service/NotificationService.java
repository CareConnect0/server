package smwu.heartcall.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.notification.dto.NotificationDetailResponseDto;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.notification.enums.NotificationType;
import smwu.heartcall.domain.notification.repository.NotificationRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.global.fcm.service.FcmService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    public static final String AUDIO_EMERGENCY_CONTENT = "음성 자동 긴급 호출";
    public static final String DIRECT_EMERGENCY_CONTENT = "직접 긴급 호출";

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final RelationRepository relationRepository;

    // 메시지를 보낸 사람, 메시지를 보낼 사람
    // 피보호자가 스케줄 생성 시 -> 보호자에게 알림 전송
    // 보호자가 스케줄 생성 시 -> 해당 피보호자에게 알림 전송
    @Transactional
    public void sendScheduleCreatedNotifications(User dependent, String content) {
        DependentRelation relation = relationRepository.findByDependentOrElseThrow(dependent);
        sendNotification(dependent, relation.getGuardian(), NotificationType.SCHEDULE_CREATE, content);
    }

    @Transactional
    public void sendScheduleCreatedByGuardianNotifications(User dependent, User guardian, String content) {
        sendNotification(guardian, dependent, NotificationType.SCHEDULE_CREATE_BY_GUARDIAN, content);
    }

    @Transactional
    public void sendChatNotifications(User sender, User receiver, String content) {
        sendNotification(sender, receiver, NotificationType.CHAT, content);
    }

    @Transactional
    public void sendEmergencyNotifications(User dependent, String content) {
        DependentRelation relation = relationRepository.findByDependentOrElseThrow(dependent);
        sendNotification(dependent, relation.getGuardian(), NotificationType.EMERGENCY, content);
    }

    @Transactional
    public void sendNotification(User sender, User receiver, NotificationType notificationType, String content) {
        String title = notificationType.formatTitle(sender.getName());

        Notification notification = Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .title(title)
                .content(content)
                .build();

        notificationRepository.save(notification);
        fcmService.sendNotification(receiver, notification); // fcm 설정 후 활성화
    }

    @Transactional
    public List<NotificationDetailResponseDto> getNotifications(User user) {
        List<Notification> notifications = notificationRepository.findAllByReceiverOrderByCreatedAtDesc(user);
        List<NotificationDetailResponseDto> responseDtoList = notifications.stream().map(NotificationDetailResponseDto::of).toList();

        for(Notification notification : notifications) {
            notification.read();
        }
        return responseDtoList;
    }

    @Transactional
    public void deleteAllNotifications(User user) {
        List<Notification> notifications = notificationRepository.findAllByReceiver(user);
        notificationRepository.deleteAll(notifications);
    }

    @Transactional
    public void deleteNotification(User user, Long notificationId) {
        Notification notification = notificationRepository.findByIdAndUserOrElseThrow(notificationId, user);
        notificationRepository.delete(notification);
    }
}
