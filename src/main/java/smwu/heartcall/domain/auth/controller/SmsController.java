package smwu.heartcall.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.auth.dto.SendCodeRequestDto;
import smwu.heartcall.domain.auth.dto.VerifyCodeRequestDto;
import smwu.heartcall.domain.auth.service.SmsService;
import smwu.heartcall.global.response.BasicResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/verification-code")
    public ResponseEntity<BasicResponse<Void>> sendVerificationCode(
            @RequestBody @Valid SendCodeRequestDto responseDto
    ) {
        smsService.sendVerificationCode(responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse.of("휴대폰 인증번호 전송이 완료되었습니다."));
    }

    @PatchMapping("/verification-code")
    public ResponseEntity<BasicResponse<Void>> verifyCode(
            @RequestBody @Valid VerifyCodeRequestDto requestDto
    ) {
        smsService.verifyCode(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse.of("휴대폰 인증이 완료되었습니다."));
    }
}
