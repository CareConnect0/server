package smwu.heartcall.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.global.entity.BaseTimeEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DependentRelation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private User guardian;

    @OneToOne
    @JoinColumn(name = "dependent_id")
    private User dependent;
}
