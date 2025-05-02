package smwu.heartcall.global.fcm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.heartcall.global.fcm.dto.FcmTokenRequestDto;
import smwu.heartcall.global.fcm.service.FcmService;
import smwu.heartcall.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/fcm"))
public class FcmController {
    private final FcmService fcmService;

    @PostMapping("/register")
    public ResponseEntity<?> registerToken(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FcmTokenRequestDto requestDto
    ) {
        fcmService.saveOrUpdateToken(userDetails.getUser(), requestDto);
        return ResponseEntity.ok("토큰 등록 완료");
    }
}
