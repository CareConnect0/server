package smwu.heartcall.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;

@Getter
@Builder
public class DependentDetailResponseDto {
    private Long dependentId;
    private String username;
    private String name;

    public static DependentDetailResponseDto of(DependentRelation relation) {
        User dependent = relation.getDependent();
        return DependentDetailResponseDto.builder()
                .dependentId(dependent.getId())
                .username(dependent.getUsername())
                .name(dependent.getName())
                .build();
    }
}
