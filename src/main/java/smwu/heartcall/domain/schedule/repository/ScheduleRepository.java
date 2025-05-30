package smwu.heartcall.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smwu.heartcall.domain.schedule.entity.Schedule;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.ScheduleErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserAndStartTimeBetweenOrderByStartTimeAsc(User user, LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Schedule> findByUserAndId(User user, Long scheduleId);

    @Query(value = """
        SELECT DISTINCT s.startTime
        FROM Schedule s
        WHERE s.user.id = :userId
        AND YEAR(s.startTime)  = :year
        AND MONTH(s.startTime) = :month
    """)
    List<LocalDateTime> findAllDateTimeByUserAndMonth(Long userId, Integer year, Integer month);

    default List<Schedule> findSchedulesOfDay(User user, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return findAllByUserAndStartTimeBetweenOrderByStartTimeAsc(user, startOfDay, endOfDay);
    }

    default Schedule findByUserAndIdOrElseThrow(User user, Long scheduleId) {
        return findByUserAndId(user, scheduleId).orElseThrow(()
                -> new CustomException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));
    }
}
