package smwu.heartcall.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;

    @Setter
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
