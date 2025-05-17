package smwu.heartcall.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.schedule.dto.GuardianCreateScheduleRequestDto;
import smwu.heartcall.domain.schedule.dto.GuardianEditScheduleRequestDto;
import smwu.heartcall.domain.schedule.dto.ScheduleDateResponseDto;
import smwu.heartcall.domain.schedule.dto.ScheduleDetailResponseDto;
import smwu.heartcall.domain.schedule.entity.Schedule;
import smwu.heartcall.domain.schedule.repository.ScheduleRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.RelationErrorCode;
import smwu.heartcall.global.util.LocalDatetimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuardianScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    @Transactional
    public void createSchedule(User user, GuardianCreateScheduleRequestDto requestDto) {
        User dependent = userRepository.findByIdOrElseThrow(requestDto.getDependentId());
        checkGuardianRelation(dependent, user);

        Schedule schedule = Schedule.builder()
                .content(requestDto.getContent())
                .startTime(LocalDatetimeUtil.fromStringToLocalDateTime(requestDto.getStartTime()))
                .user(dependent)
                .build();

        scheduleRepository.save(schedule);
    }

    public List<ScheduleDetailResponseDto> getSchedules(User user, Long dependentId, LocalDate date) {
        User dependent = userRepository.findByIdOrElseThrow(dependentId);
        checkGuardianRelation(dependent, user);

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Schedule> schedules = scheduleRepository.findSchedulesOfDay(dependent, startOfDay, endOfDay);
        return schedules.stream().map(ScheduleDetailResponseDto::of).toList();
    }

    public ScheduleDateResponseDto getScheduleDates(User user, Long dependentId, Integer year, Integer month) {
        User dependent = userRepository.findByIdOrElseThrow(dependentId);
        checkGuardianRelation(dependent, user);

        List<LocalDateTime> datetimeList = scheduleRepository.findAllDateTimeByUserAndMonth(dependent.getId(), year, month);
        return ScheduleDateResponseDto.of(datetimeList);
    }

    @Transactional
    public ScheduleDetailResponseDto editSchedule(User user, Long scheduleId, GuardianEditScheduleRequestDto requestDto) {
        User dependent = userRepository.findByIdOrElseThrow(requestDto.getDependentId());
        checkGuardianRelation(dependent, user);

        Schedule schedule = scheduleRepository.findByUserAndIdOrElseThrow(dependent, scheduleId);
        schedule.setContent(requestDto.getContent());
        schedule.setStartTime(LocalDatetimeUtil.fromStringToLocalDateTime(requestDto.getStartTime()));

        return ScheduleDetailResponseDto.of(schedule);
    }

    @Transactional
    public void deleteSchedule(User user, Long dependentId, Long scheduleId) {
        User dependent = userRepository.findByIdOrElseThrow(dependentId);
        checkGuardianRelation(dependent, user);

        Schedule schedule = scheduleRepository.findByUserAndIdOrElseThrow(dependent, scheduleId);
        scheduleRepository.delete(schedule);
    }

    private void checkGuardianRelation(User dependent, User guardian) {
        if(!relationRepository.existsByDependentAndGuardian(dependent, guardian)) {
            throw new CustomException(RelationErrorCode.NOT_A_GUARDIAN_OF_USER);
        }
    }
}
