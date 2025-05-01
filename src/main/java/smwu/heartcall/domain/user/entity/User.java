package smwu.heartcall.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.global.entity.BaseTimeEntity;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    public void withdraw() {
        this.userStatus = UserStatus.WITHDRAWAL;
    }
}
