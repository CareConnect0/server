package smwu.heartcall.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.notification.dto.NotificationDetailResponseDto;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.notification.enums.NotificationType;
import smwu.heartcall.domain.notification.repository.NotificationRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.fcm.service.FcmService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Transactional
    public void sendNotification(User receiver, NotificationType notificationType, String content) {
        Notification notification = Notification.builder()
                .receiver(receiver)
                .title(notificationType.formatTitle(receiver.getName()))
                .content(content)
                .build();

        notificationRepository.save(notification);
        fcmService.sendNotification(receiver, notification);
    }

    @Transactional
    public List<NotificationDetailResponseDto> getNotifications(User user) {
        List<Notification> notifications = notificationRepository.findAllByReceiver(user);
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
