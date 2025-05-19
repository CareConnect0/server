package smwu.heartcall.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ScheduleDateResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> dateList;

    public static ScheduleDateResponseDto of(List<LocalDateTime> dateTimeList) {
        List<LocalDate> dateList = dateTimeList.stream().map(LocalDateTime::toLocalDate)
                .sorted()
                .toList();

        return ScheduleDateResponseDto.builder()
                .dateList(dateList)
                .build();
    }
}
