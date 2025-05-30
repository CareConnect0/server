package smwu.heartcall.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.notification.enums.NotificationType;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.NotificationErrorCode;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(User receiver);
    List<Notification> findAllByReceiverOrderByCreatedAtDesc(User receiver);
    Optional<Notification> findByIdAndReceiver(Long id, User receiver);

    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.receiver = :receiver AND n.isRead = false AND n.notificationType = :type")
    boolean existsUnreadByType(User receiver, NotificationType type);

    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.receiver = :receiver AND n.isRead = false AND n.notificationType IN (:types)")
    boolean existsUnreadByTypes(User receiver, List<NotificationType> types);

    default Notification findByIdAndUserOrElseThrow(Long notificationId, User user) {
        return findByIdAndReceiver(notificationId, user).orElseThrow(() ->
                new CustomException(NotificationErrorCode.NOTIFICATION_NOT_FOUND)
        );
    }
}
