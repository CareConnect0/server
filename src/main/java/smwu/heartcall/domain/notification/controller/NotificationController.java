package smwu.heartcall.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.notification.dto.NotificationDetailResponseDto;
import smwu.heartcall.domain.notification.dto.UnReadNotificationResponseDto;
import smwu.heartcall.domain.notification.service.NotificationService;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<BasicResponse<List<NotificationDetailResponseDto>>> getNotifications(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<NotificationDetailResponseDto> responseDto = notificationService.getNotifications(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("알림 조회 완료", responseDto));
    }

    @GetMapping("/unread")
    public ResponseEntity<BasicResponse<UnReadNotificationResponseDto>> checkUnreadNotifications(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UnReadNotificationResponseDto responseDto = notificationService.checkUnreadNotifications(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("알림 여부 조회 완료", responseDto));
    }

    @DeleteMapping("/all")
    public ResponseEntity<BasicResponse<Void>> deleteAllNotifications(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        notificationService.deleteAllNotifications(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("전체 알림 삭제 완료"));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<BasicResponse<Void>> deleteNotification(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long notificationId
    ) {
        notificationService.deleteNotification(userDetails.getUser(), notificationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("개별 알림 삭제 완료"));
    }
}
