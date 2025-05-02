package smwu.heartcall.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.schedule.dto.CreateScheduleRequestDto;
import smwu.heartcall.domain.schedule.dto.EditScheduleRequestDto;
import smwu.heartcall.domain.schedule.dto.ScheduleDetailResponseDto;
import smwu.heartcall.domain.schedule.entity.Schedule;
import smwu.heartcall.domain.schedule.repository.ScheduleRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.fcm.repository.FcmRepository;
import smwu.heartcall.global.fcm.service.FcmService;
import smwu.heartcall.global.util.LocalDatetimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final FcmService fcmService;
    private final FcmRepository fcmRepository;

    @Transactional
    public void createSchedule(User user, CreateScheduleRequestDto requestDto) {
        Schedule schedule = Schedule.builder()
                .content(requestDto.getContent())
                .startTime(LocalDatetimeUtil.fromStringToLocalDateTime(requestDto.getStartTime()))
                .user(user)
                .build();

        scheduleRepository.save(schedule);
        String token = fcmRepository.findByUserOrElseThrow(user).getToken();
        fcmService.sendNotification(token, "일정 알림", "새로운 일정이 등록되었습니다.");
    }

    public List<ScheduleDetailResponseDto> getSchedules(User user, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Schedule> schedules = scheduleRepository.findAllByUserAndStartTimeBetween(user, startOfDay, endOfDay);
        return schedules.stream().map(ScheduleDetailResponseDto::of).toList();
    }

    @Transactional
    public ScheduleDetailResponseDto editSchedule(User user, Long scheduleId, EditScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findByUserAndIdOrElseThrow(user, scheduleId);
        schedule.setContent(requestDto.getContent());
        schedule.setStartTime(LocalDatetimeUtil.fromStringToLocalDateTime(requestDto.getStartTime()));

        return ScheduleDetailResponseDto.of(schedule);
    }

    @Transactional
    public void deleteSchedule(User user, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByUserAndIdOrElseThrow(user, scheduleId);
        scheduleRepository.delete(schedule);
    }
}
