package smwu.heartcall.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.NotificationErrorCode;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(User receiver);
    List<Notification> findAllByReceiverOrderByCreatedAtDesc(User receiver);
    Optional<Notification> findByIdAndReceiver(Long id, User receiver);

    boolean existsByReceiverAndIsRead(User receiver, boolean isRead);

    default Notification findByIdAndUserOrElseThrow(Long notificationId, User user) {
        return findByIdAndReceiver(notificationId, user).orElseThrow(() ->
                new CustomException(NotificationErrorCode.NOTIFICATION_NOT_FOUND)
        );
    }
}
