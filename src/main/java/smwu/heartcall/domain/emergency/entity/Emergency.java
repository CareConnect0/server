package smwu.heartcall.domain.emergency.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.converter.StringArrayConverter;
import smwu.heartcall.global.entity.BaseTimeEntity;

import java.util.List;

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

    @Convert(converter = StringArrayConverter.class)
    @Column(length = 200)
    private List<String> keyword;

    @Column(length = 500)
    private String audioUrl;

    private boolean isChecked = false;
    private boolean isDeleted = false;

    public void check() {
        this.isChecked = true;
    }
}
