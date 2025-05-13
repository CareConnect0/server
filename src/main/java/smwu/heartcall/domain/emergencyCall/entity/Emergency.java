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
public class Emergency extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependent_id", nullable = false)
    private User dependent;

    @Column(length = 200)
    private String keyword;

    @Column(nullable = false, length = 500)
    private String audioUrl;

    private boolean isChecked = false;
    private boolean isDeleted = false;

    public void check() {
        this.isChecked = true;
    }
}
