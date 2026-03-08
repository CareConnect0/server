package smwu.heartcall.domain.aiAssistant.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.aiAssistant.enums.SenderType;
import smwu.heartcall.global.entity.BaseTimeEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AiAssistantMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ai_assistant_room_id", nullable = false)
    private AiAssistantRoom aiAssistantRoom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SenderType senderType;

    @Column(nullable = false, length = 500)
    private String content;
}
