package smwu.heartcall.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.notification.enums.NotificationType;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.entity.BaseTimeEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 300자
    @Column(nullable = false, length = 300)
    private String content;

    private boolean isRead = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
