package smwu.heartcall.domain.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.schedule.dto.*;
import smwu.heartcall.domain.schedule.service.GuardianScheduleService;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guardian/schedules")
@PreAuthorize("@userPermissionChecker.isGuardian(authentication)")
public class GuardianScheduleController {
    private final GuardianScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<BasicResponse<Void>> createSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid GuardianCreateScheduleRequestDto requestDto
    ) {
        scheduleService.createSchedule(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of("피보호자 일정 생성 완료"));
    }

    @GetMapping
    public ResponseEntity<BasicResponse<List<ScheduleDetailResponseDto>>> getSchedules(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("dependentId") Long dependentId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ScheduleDetailResponseDto> responseDtoList = scheduleService.getSchedules(userDetails.getUser(), dependentId, date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("일정 조회 완료", responseDtoList));
    }

    @GetMapping("/dates")
    public ResponseEntity<BasicResponse<ScheduleDateResponseDto>> getScheduleDates(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("dependentId") Long dependentId,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month
    ) {
        ScheduleDateResponseDto responseDtoList = scheduleService.getScheduleDates(userDetails.getUser(), dependentId, year, month);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("해당 월의 일정 조회 완료", responseDtoList));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<BasicResponse<ScheduleDetailResponseDto>> editSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long scheduleId,
            @RequestBody @Valid GuardianEditScheduleRequestDto requestDto
    ) {
        ScheduleDetailResponseDto responseDto = scheduleService.editSchedule(userDetails.getUser(), scheduleId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("일정 수정 완료", responseDto));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<BasicResponse<Void>> deleteSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long scheduleId,
            @RequestParam("dependentId") Long dependentId
    ) {
        scheduleService.deleteSchedule(userDetails.getUser(), dependentId, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("일정 삭제 완료"));
    }
}
