package smwu.heartcall.domain.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import smwu.heartcall.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleDetailResponseDto {
    private Long scheduleId;
    private String content;
    private LocalDateTime startTime;

    public static ScheduleDetailResponseDto of(Schedule schedule) {
        return ScheduleDetailResponseDto.builder()
                .scheduleId(schedule.getId())
                .content(schedule.getContent())
                .startTime(schedule.getStartTime())
                .build();
    }
}
