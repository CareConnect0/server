package smwu.heartcall.global.fcm.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.user.entity.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

