package smwu.heartcall.domain.emergencyCall.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.entity.BaseTimeEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmergencyCall extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dependent_id", nullable = false)
    private User dependent;

    private boolean isSolved = false;
}
