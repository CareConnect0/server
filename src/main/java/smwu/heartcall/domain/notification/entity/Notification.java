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
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private Long targetId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    private String relatedUrl;

    private boolean isRead = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public void read() {
        this.isRead = true;
    }
}
